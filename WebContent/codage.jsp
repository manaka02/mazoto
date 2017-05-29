<%@page import="local.baledo.root.codage.service.BruitageService"%>
<%@page import="local.baledo.root.codage.service.CodageService"%>
<%@page import="local.baledo.root.codage.object.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/materialize.css">
</head>
<body>
	<% if(request.getParameter("message") != null && request.getParameter("n") != null && request.getParameter("k") != null){
			String message = request.getParameter("message").trim();
			int n = Integer.parseInt(request.getParameter("n").trim());
			int k = Integer.parseInt(request.getParameter("k").trim());
			Matrice motinfo = CodageService.toBinary(message, k);
			%>
		<h2>Mot d'information : </h2>
		<%	for(int i=0;i<motinfo.getRows();i++){
				for(int j=0;j<motinfo.getColumns();j++){
					out.print(motinfo.getValue(i, j));
				}%>
			<br>
		<% 	} %>
		<h2>Matrice génératrice : </h2>
		<%	Matrice generatrice = CodageService.matriceGeneratrice(n, k); %>
		<%	for(int i=0;i<generatrice.getRows();i++){
				for(int j=0;j<generatrice.getColumns();j++){
					out.print(generatrice.getValue(i, j));
				}%>
			<br>
		<% 	} %>
		<h2>Mot de code : </h2>
		<%	Matrice code = CodageService.getMotDeCode(generatrice, motinfo); %>
		<%	for(int i=0;i<code.getRows();i++){
				for(int j=0;j<code.getColumns();j++){
					out.print(code.getValue(i, j));
				}%>
			<br>
		<% 	} %>
		<h2>Mot bruité : </h2>
		<%	BruitageService.addNose(code); %>
		<%	for(int i=0;i<code.getRows();i++){
				for(int j=0;j<code.getColumns();j++){
					out.print(code.getValue(i, j));
				}%>
			<br>
		<% 	} %>
		<h2>Matrice de contrôle : </h2>
		<%	Matrice controle = CodageService.getControle(generatrice, n, k); %>
		<%	for(int i=0;i<controle.getRows();i++){
				for(int j=0;j<controle.getColumns();j++){
					out.print(controle.getValue(i, j));
				}%>
			<br>
		<% 	} %>
		<% 
			Matrice possibilite = CodageService.generateAllPossibilite(n);
			Matrice tpossible = possibilite.getMatriceTranspose();
			Matrice syndrome = CodageService.getSyndrome(controle, tpossible);
			TableauDecodage tab = CodageService.getTableauDecodage(syndrome, tpossible);
			out.print(tab);
			CodageService.corriger(code, controle, tab);
		%>
		<h2>Après correction : </h2>
		<%	for(int i=0;i<code.getRows();i++){
				for(int j=0;j<code.getColumns();j++){
					out.print(code.getValue(i, j));
				}%>
			<br>
		<% 	} %>
		<!-- <% //Matrice decode = CodageService.decoder(generatrice,code); %>
		<h2>Après décodage : </h2>
		<%	/*for(int i=0;i<decode.getRows();i++){
				for(int j=0;j<decode.getColumns();j++){
					out.print(decode.getValue(i, j));
				}*/%>
			<br>
		<% 	//} %> -->
	<%} %>
		

</body>
</html>
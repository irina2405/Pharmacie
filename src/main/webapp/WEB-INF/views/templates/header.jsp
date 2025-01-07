<%@page import="com.projet.pharmacie.util.*"%>
<%@page import="com.projet.pharmacie.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.time.*"%>
<%@page import="java.time.format.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pharmacie</title>
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/sidebar.css" rel="stylesheet">
    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <style>
        .btn-primary {
            background-color: white;
            color: #787878;
            border-color: #c9c9c9;
        }
        .btn-danger {
            background-color: white;
            color: #787878;
            border-color: #c9c9c9;

        }
    </style>
</head>
<body>

<!-- Barre de navigation -->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Pharmacie</a>
        </div>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="nav navbar-nav">
                <li><a href="/">Accueil</a></li>
            </ul>
        </div>
    </div>
</nav>

<%@ include file="sidebar.jsp" %>
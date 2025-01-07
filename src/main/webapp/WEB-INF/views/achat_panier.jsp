<%@ include file="/WEB-INF/views/templates/header.jsp"  %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<% 
    // Récupération des produits pour le dropdown
    Produit[] allProduits = (Produit[]) request.getAttribute("all_produit");
    Client[] allClients = (Client[]) request.getAttribute("all_client");

    // Récupération du panier depuis la session
    HashMap<Integer, Produit> panier = (HashMap<Integer, Produit>) session.getAttribute("panier");
%>

<html>
<body>
    <div class="container">
        <h1>Gestion du Panier</h1>
        <!-- Formulaire pour ajouter au panier -->
        <form action="/mise_en_panier" method="post">
            <div class="form-group">
                <label for="idProduit">Choisissez un produit :</label>
                <select name="idProduit" class="form-control">
                    <% if (allProduits != null) { %>
                        <% for (Produit produit : allProduits) { %>
                            <option value="<%= produit.getId() %>"><%= produit.getNom() %> - Prix : <%= produit.getDenorm_prix_vente() %></option>
                        <% } %>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <label for="quantite">Quantité :</label>
                <input type="number" name="quantite" class="form-control" min="1" required />
            </div>
            <button type="submit" class="btn btn-primary">Ajouter au panier</button>
        </form>

        <hr>

        <!-- Tableau pour afficher les produits dans le panier -->
        <h2>Contenu du Panier</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Produit</th>
                    <th>Quantité</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% if (panier != null && !panier.isEmpty()) { %>
                    <% for (Produit produit : panier.values()) { %>
                        <tr>
                            <td><%= produit.getNom() %></td>
                            <td><%= produit.getQtPanier() %></td>
                            <td>
                                <form action="/elever_du_panier" method="post" style="display: inline;">
                                    <input type="hidden" name="idProduit" value="<%= produit.getId() %>" />
                                    <button type="submit" class="btn btn-danger btn-sm">Enlever</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="3">Votre panier est vide.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <% if (panier != null && !panier.isEmpty()) { %>
            <form action="/valider_panier" method="get">
                <div class="form-group">
                    <p><strong>Total :</strong> <%= Produit.getTotal(panier) %></p>
                    <label for="id_client">Client :</label>
                    <select name="id_client" class="form-control">
                        <option value="">Client divers</option>
                        <% for (Client client : allClients) { %>
                            <option value="<%= client.getId() %>"><%= client.getNom() %></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="total_paye">Total payé :</label>
                    <input name="total_paye" type="number" class="form-control" placeholder="Total payé" value="<%= Produit.getTotal(panier) %>" />
                </div>
                <button type="submit" class="btn btn-success">Valider mon panier</button>
            </form>
        <% } %>
    </div>
</body>
</html>

<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Produit_fournisseur[] all = (Produit_fournisseur[]) request.getAttribute("all");
        Produit_fournisseur currentProduit_fournisseur = (Produit_fournisseur) request.getAttribute("currentProduit_fournisseur");
        Fournisseur[] allFournisseur = (Fournisseur[]) request.getAttribute("allFournisseur");
        Produit[] allProduit = (Produit[]) request.getAttribute("allProduit");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Fournisseur</th>
                    <th>Produit</th>
                    <th>Date </th>
                    <th>Prix</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getFournisseur() != null ? all[i].getFournisseur().getNom() : "" %> </td>
                    <td><%= all[i].getProduit() != null ? all[i].getProduit().getNom() : "" %> </td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getPrix() %></td>
                    <td>
                        <a href="/TraitProduit_fournisseur/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitProduit_fournisseur/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                    </td>
                </tr>
                <% } %>
            </table>
            <center><nav><ul class="pagination pagination-lg"></ul></nav></center>
            <script src="/assets/myjs/pagination.js"></script>
        </div>
        <div class="col-md-1">
        </div>
        <div class="col-md-4">
            <form action="/InitProduit_fournisseur" method="post">
                <input type="hidden" name="id" value="<%= currentProduit_fournisseur != null ? currentProduit_fournisseur.getId() : 0 %>">
                <% if (currentProduit_fournisseur != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="fournisseur">Fournisseur</label>
                    <select name="fournisseur" id="fournisseur" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allFournisseur.length; j++) { %>
                        <option value="<%= allFournisseur[j].getId() %>" <%= currentProduit_fournisseur != null && currentProduit_fournisseur.getFournisseur() != null && currentProduit_fournisseur.getFournisseur().getId() == allFournisseur[j].getId() ? "selected" : "" %>><%= allFournisseur[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="produit">Produit</label>
                    <select name="produit" id="produit" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit.length; j++) { %>
                        <option value="<%= allProduit[j].getId() %>" <%= currentProduit_fournisseur != null && currentProduit_fournisseur.getProduit() != null && currentProduit_fournisseur.getProduit().getId() == allProduit[j].getId() ? "selected" : "" %>><%= allProduit[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="date" name="date_" id="date_" class="form-control" value="<%= currentProduit_fournisseur != null ? currentProduit_fournisseur.getDate_() : "" %>">
                </div>
                <div class="form-group">
                    <label for="prix">Prix</label>
                    <input type="number" name="prix" id="prix" class="form-control" value="<%= currentProduit_fournisseur != null ? currentProduit_fournisseur.getPrix() : "" %>">
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
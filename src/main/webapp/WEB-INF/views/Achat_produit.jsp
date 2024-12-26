<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Achat_produit[] all = (Achat_produit[]) request.getAttribute("all");
        Achat_produit currentAchat_produit = (Achat_produit) request.getAttribute("currentAchat_produit");
        Fournisseur[] allFournisseur = (Fournisseur[]) request.getAttribute("allFournisseur");
        Produit[] allProduit = (Produit[]) request.getAttribute("allProduit");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Date </th>
                    <th>Qt produit</th>
                    <th>Denorm prix achat</th>
                    <th>Fournisseur</th>
                    <th>Produit</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getQt_produit() %></td>
                    <td><%= all[i].getDenorm_prix_achat() %></td>
                    <td><%= all[i].getFournisseur() != null ? all[i].getFournisseur().getNom() : "" %> </td>
                    <td><%= all[i].getProduit() != null ? all[i].getProduit().getNom() : "" %> </td>
                    <td>
                        <a href="/TraitAchat_produit/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitAchat_produit/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
            <form action="/InitAchat_produit" method="post">
                <input type="hidden" name="id" value="<%= currentAchat_produit != null ? currentAchat_produit.getId() : 0 %>">
                <% if (currentAchat_produit != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="datetime-local" name="date_" id="date_" class="form-control" value="<%= currentAchat_produit != null ? currentAchat_produit.getDate_() : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>"  step="1">
                </div>
                <div class="form-group">
                    <label for="qt_produit">Qt produit</label>
                    <input type="number" name="qt_produit" id="qt_produit" class="form-control" value="<%= currentAchat_produit != null ? currentAchat_produit.getQt_produit() : "" %>">
                </div>
                <div class="form-group">
                    <label for="denorm_prix_achat">Denorm prix achat</label>
                    <input type="number" name="denorm_prix_achat" id="denorm_prix_achat" class="form-control" value="<%= currentAchat_produit != null ? currentAchat_produit.getDenorm_prix_achat() : "" %>">
                </div>
                <div class="form-group">
                    <label for="fournisseur">Fournisseur</label>
                    <select name="fournisseur" id="fournisseur" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allFournisseur.length; j++) { %>
                        <option value="<%= allFournisseur[j].getId() %>" <%= currentAchat_produit != null && currentAchat_produit.getFournisseur() != null && currentAchat_produit.getFournisseur().getId() == allFournisseur[j].getId() ? "selected" : "" %>><%= allFournisseur[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="produit">Produit</label>
                    <select name="produit" id="produit" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit.length; j++) { %>
                        <option value="<%= allProduit[j].getId() %>" <%= currentAchat_produit != null && currentAchat_produit.getProduit() != null && currentAchat_produit.getProduit().getId() == allProduit[j].getId() ? "selected" : "" %>><%= allProduit[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
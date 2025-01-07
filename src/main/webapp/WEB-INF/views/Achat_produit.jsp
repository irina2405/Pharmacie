<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Achat_produit[] all = (Achat_produit[]) request.getAttribute("all");
        Achat_produit currentAchat_produit = (Achat_produit) request.getAttribute("currentAchat_produit");
        Produit_fournisseur[] allProduit_fournisseur = (Produit_fournisseur[]) request.getAttribute("allProduit_fournisseur");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Date </th>
                    <th>Qt produit</th>
                    <th>Produit fournisseur</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getQt_produit() %></td>
                    <td><%= all[i].getProduit_fournisseur() != null ? all[i].getProduit_fournisseur().getId() + " -- " + all[i].getProduit_fournisseur().getFournisseur().getNom() + " -- " + all[i].getProduit_fournisseur().getProduit().getNom() + " -- " + all[i].getProduit_fournisseur().getPrix() : "" %> </td>
                    <td>
                        <%-- <a href="/TraitAchat_produit/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a> --%>
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
                    <label for="produit_fournisseur">Fournisseur produit</label>
                    <select name="produit_fournisseur" id="produit_fournisseur" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit_fournisseur.length; j++) { %>
                        <option value="<%= allProduit_fournisseur[j].getId() %>" <%= currentAchat_produit != null && currentAchat_produit.getProduit_fournisseur() != null && currentAchat_produit.getProduit_fournisseur().getId() == allProduit_fournisseur[j].getId() ? "selected" : "" %>><%= allProduit_fournisseur[j].getId() + " -- " + allProduit_fournisseur[j].getFournisseur().getNom() + " -- " + allProduit_fournisseur[j].getProduit().getNom() + " - " + " -- " + allProduit_fournisseur[j].getPrix()  %>
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
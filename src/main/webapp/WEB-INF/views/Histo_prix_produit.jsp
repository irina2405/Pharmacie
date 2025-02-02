<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Histo_prix_produit[] all = (Histo_prix_produit[]) request.getAttribute("all");
        Histo_prix_produit currentHisto_prix_produit = (Histo_prix_produit) request.getAttribute("currentHisto_prix_produit");
        Produit[] allProduit = (Produit[]) request.getAttribute("allProduit");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Date </th>
                    <th>Prix vente produit</th>
                    <th>Produit</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getPrix_vente_produit() %></td>
                    <td><%= all[i].getProduit() != null ? all[i].getProduit().getNom() : "" %> </td>
                    <td>
                        <%-- <a href="/TraitHisto_prix_produit/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a> --%>
                        <%-- <a href="/InitHisto_prix_produit/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a> --%>
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
            <form action="/InitHisto_prix_produit" method="post">
                <input type="hidden" name="id" value="<%= currentHisto_prix_produit != null ? currentHisto_prix_produit.getId() : 0 %>">
                <% if (currentHisto_prix_produit != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="date" name="date_" id="date_" class="form-control" value="<%= currentHisto_prix_produit != null ? currentHisto_prix_produit.getDate_() : "" %>">
                </div>
                <div class="form-group">
                    <label for="prix_vente_produit">Prix vente produit</label>
                    <input type="number" name="prix_vente_produit" id="prix_vente_produit" class="form-control" value="<%= currentHisto_prix_produit != null ? currentHisto_prix_produit.getPrix_vente_produit() : "" %>">
                </div>
                <div class="form-group">
                    <label for="produit">Produit</label>
                    <select name="produit" id="produit" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit.length; j++) { %>
                        <option value="<%= allProduit[j].getId() %>" <%= currentHisto_prix_produit != null && currentHisto_prix_produit.getProduit() != null && currentHisto_prix_produit.getProduit().getId() == allProduit[j].getId() ? "selected" : "" %>><%= allProduit[j].getNom() %>
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
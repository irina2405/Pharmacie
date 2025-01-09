<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Detail_facture[] all = (Detail_facture[]) request.getAttribute("all");
        Detail_facture currentDetail_facture = (Detail_facture) request.getAttribute("currentDetail_facture");
        Produit[] allProduit = (Produit[]) request.getAttribute("allProduit");
        Facture[] allFacture = (Facture[]) request.getAttribute("allFacture");
        Categorie[] allCategorie = (Categorie[]) request.getAttribute("allCategorie");
        
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
        <form action="/InitDetail_facture" method="get">
            <div class="form-group">
                <label for="categorie">Categorie</label>
                <select name="id_categorie" id="categorie" class="form-control">
                    <option value="">-- choose --</option>
                    <% for (int j = 0; j < allCategorie.length; j++) { %>
                    <option value="<%= allCategorie[j].getId() %>" ><%= allCategorie[j].getNom() %>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <label for="indice">Indice d'age</label>
                <select name="id_indice" id="indice" class="form-control">
                    <option value="1">Bebe - 0 a 1</option>
                    <option value="2">Enfant - 1 a 6</option>
                    <option value="3">Ado - 6 a 18</option>
                    <option value="4">Adulte - 18 +</option>
                </select>
            </div>
            <button type="submit" class="btn btn-default">Rechercher</button>

        </form>
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Produit</th>
                    <th>Facture</th>
                    <th>Denorm prix vente</th>
                    <th>Qt produit</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getProduit() != null ? all[i].getProduit().getNom() : "" %> </td>
                    <td><%= all[i].getFacture() != null ? all[i].getFacture().getDate_() : "" %> </td>
                    <td><%= all[i].getDenorm_prix_vente() %></td>
                    <td><%= all[i].getQt_produit() %></td>
                    <td>
                        <%-- <a href="/TraitDetail_facture/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a> --%>
                        <%-- <a href="/InitDetail_facture/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a> --%>
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
            <form action="/InitDetail_facture" method="post">
                <input type="hidden" name="id" value="<%= currentDetail_facture != null ? currentDetail_facture.getId() : 0 %>">
                <% if (currentDetail_facture != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="produit">Produit</label>
                    <select name="produit" id="produit" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit.length; j++) { %>
                        <option value="<%= allProduit[j].getId() %>" <%= currentDetail_facture != null && currentDetail_facture.getProduit() != null && currentDetail_facture.getProduit().getId() == allProduit[j].getId() ? "selected" : "" %>><%= allProduit[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="facture">Facture</label>
                    <select name="facture" id="facture" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allFacture.length; j++) { %>
                        <option value="<%= allFacture[j].getId() %>" <%= currentDetail_facture != null && currentDetail_facture.getFacture() != null && currentDetail_facture.getFacture().getId() == allFacture[j].getId() ? "selected" : "" %>><%= allFacture[j].getDate_() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="denorm_prix_vente">Denorm prix vente</label>
                    <input type="number" name="denorm_prix_vente" id="denorm_prix_vente" class="form-control" value="<%= currentDetail_facture != null ? currentDetail_facture.getDenorm_prix_vente() : "" %>">
                </div>
                <div class="form-group">
                    <label for="qt_produit">Qt produit</label>
                    <input type="number" name="qt_produit" id="qt_produit" class="form-control" value="<%= currentDetail_facture != null ? currentDetail_facture.getQt_produit() : "" %>">
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
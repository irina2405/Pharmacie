<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Produit[] all = (Produit[]) request.getAttribute("all");
        Produit currentProduit = (Produit) request.getAttribute("currentProduit");
        Unite[] allUnite = (Unite[]) request.getAttribute("allUnite");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Nom</th>
                    <th>Denorm prix vente</th>
                    <th>Min age</th>
                    <th>Max age</th>
                    <th>Unite</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getNom() %></td>
                    <td><%= all[i].getDenorm_prix_vente() %></td>
                    <td><%= all[i].getMin_age() %></td>
                    <td><%= all[i].getMax_age() %></td>
                    <td><%= all[i].getUnite() != null ? all[i].getUnite().getNom() : "" %> </td>
                    <td>
                        <a href="/TraitProduit/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitProduit/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
            <form action="/InitProduit" method="post">
                <input type="hidden" name="id" value="<%= currentProduit != null ? currentProduit.getId() : 0 %>">
                <% if (currentProduit != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="nom">Nom</label>
                    <input type="text" name="nom" id="nom" class="form-control" value="<%= currentProduit != null ? currentProduit.getNom() : "" %>">
                </div>
                <div class="form-group">
                    <label for="denorm_prix_vente">Denorm prix vente</label>
                    <input type="number" name="denorm_prix_vente" id="denorm_prix_vente" class="form-control" value="<%= currentProduit != null ? currentProduit.getDenorm_prix_vente() : "" %>">
                </div>
                <div class="form-group">
                    <label for="min_age">Min age</label>
                    <input type="number" name="min_age" id="min_age" class="form-control" value="<%= currentProduit != null ? currentProduit.getMin_age() : "" %>">
                </div>
                <div class="form-group">
                    <label for="max_age">Max age</label>
                    <input type="number" name="max_age" id="max_age" class="form-control" value="<%= currentProduit != null ? currentProduit.getMax_age() : "" %>">
                </div>
                <div class="form-group">
                    <label for="unite">Unite</label>
                    <select name="unite" id="unite" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allUnite.length; j++) { %>
                        <option value="<%= allUnite[j].getId() %>" <%= currentProduit != null && currentProduit.getUnite() != null && currentProduit.getUnite().getId() == allUnite[j].getId() ? "selected" : "" %>><%= allUnite[j].getNom() %>
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
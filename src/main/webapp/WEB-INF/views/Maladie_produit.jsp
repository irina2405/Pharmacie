<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Maladie_produit[] all = (Maladie_produit[]) request.getAttribute("all");
        Maladie_produit currentMaladie_produit = (Maladie_produit) request.getAttribute("currentMaladie_produit");
        Produit[] allProduit = (Produit[]) request.getAttribute("allProduit");
        Maladie[] allMaladie = (Maladie[]) request.getAttribute("allMaladie");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Produit</th>
                    <th>Maladie</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getProduit() != null ? all[i].getProduit().getNom() : "" %> </td>
                    <td><%= all[i].getMaladie() != null ? all[i].getMaladie().getNom() : "" %> </td>
                    <td>
                        <a href="/TraitMaladie_produit/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitMaladie_produit/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
            <form action="/InitMaladie_produit" method="post">
                <input type="hidden" name="id" value="<%= currentMaladie_produit != null ? currentMaladie_produit.getId() : 0 %>">
                <% if (currentMaladie_produit != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="produit">Produit</label>
                    <select name="produit" id="produit" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit.length; j++) { %>
                        <option value="<%= allProduit[j].getId() %>" <%= currentMaladie_produit != null && currentMaladie_produit.getProduit() != null && currentMaladie_produit.getProduit().getId() == allProduit[j].getId() ? "selected" : "" %>><%= allProduit[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="maladie">Maladie</label>
                    <select name="maladie" id="maladie" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allMaladie.length; j++) { %>
                        <option value="<%= allMaladie[j].getId() %>" <%= currentMaladie_produit != null && currentMaladie_produit.getMaladie() != null && currentMaladie_produit.getMaladie().getId() == allMaladie[j].getId() ? "selected" : "" %>><%= allMaladie[j].getNom() %>
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
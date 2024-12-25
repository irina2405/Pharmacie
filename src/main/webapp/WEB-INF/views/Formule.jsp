<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Formule[] all = (Formule[]) request.getAttribute("all");
        Formule currentFormule = (Formule) request.getAttribute("currentFormule");
        Matiere_premiere[] allMp = (Matiere_premiere[]) request.getAttribute("allMp");
        Produit[] allProduit = (Produit[]) request.getAttribute("allProduit");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Matiere premiere</th>
                    <th>Produit</th>
                    <th>Qt mp</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getMp() != null ? all[i].getMp().getNom() : "" %> </td>
                    <td><%= all[i].getProduit() != null ? all[i].getProduit().getNom() : "" %> </td>
                    <td><%= all[i].getQt_mp() %></td>
                    <td>
                        <a href="/TraitFormule/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitFormule/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
            <form action="/InitFormule" method="post">
                <input type="hidden" name="id" value="<%= currentFormule != null ? currentFormule.getId() : 0 %>">
                <% if (currentFormule != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="mp">Matiere premiere</label>
                    <select name="mp" id="mp" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allMp.length; j++) { %>
                        <option value="<%= allMp[j].getId() %>" <%= currentFormule != null && currentFormule.getMp() != null && currentFormule.getMp().getId() == allMp[j].getId() ? "selected" : "" %>><%= allMp[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="produit">Produit</label>
                    <select name="produit" id="produit" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allProduit.length; j++) { %>
                        <option value="<%= allProduit[j].getId() %>" <%= currentFormule != null && currentFormule.getProduit() != null && currentFormule.getProduit().getId() == allProduit[j].getId() ? "selected" : "" %>><%= allProduit[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="qt_mp">Qt mp</label>
                    <input type="number" name="qt_mp" id="qt_mp" class="form-control" value="<%= currentFormule != null ? currentFormule.getQt_mp() : "" %>">
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
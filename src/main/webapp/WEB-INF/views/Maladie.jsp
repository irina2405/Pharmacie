<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Maladie[] all = (Maladie[]) request.getAttribute("all");
        Maladie currentMaladie = (Maladie) request.getAttribute("currentMaladie");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Nom</th>
                    <th>Actions</th>
                    <th>Voir Produit concernes</th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getNom() %></td>
                    <td>
                        <a href="/TraitMaladie/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitMaladie/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                    </td>
                    <td><a href="/produits_concernes_par_maladie/<%= all[i].getId() %>">Voir</a></td>
                </tr>
                <% } %>
            </table>
            <center><nav><ul class="pagination pagination-lg"></ul></nav></center>
            <script src="/assets/myjs/pagination.js"></script>
        </div>
        <div class="col-md-1">
        </div>
        <div class="col-md-4">
            <form action="/InitMaladie" method="post">
                <input type="hidden" name="id" value="<%= currentMaladie != null ? currentMaladie.getId() : 0 %>">
                <% if (currentMaladie != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="nom">Nom</label>
                    <input type="text" name="nom" id="nom" class="form-control" value="<%= currentMaladie != null ? currentMaladie.getNom() : "" %>">
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
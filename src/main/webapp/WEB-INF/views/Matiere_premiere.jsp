<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Matiere_premiere[] all = (Matiere_premiere[]) request.getAttribute("all");
        Matiere_premiere currentMatiere_premiere = (Matiere_premiere) request.getAttribute("currentMatiere_premiere");
        Unite[] allUnite = (Unite[]) request.getAttribute("allUnite");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Nom</th>
                    <th>Unite</th>
                    <th>Qt restante</th>
                    <th>Actions</th>
                    <th>Produits concernes</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getNom() %></td>
                    <td><%= all[i].getUnite() != null ? all[i].getUnite().getNom() : "" %> </td>
                    <td><%= all[i].getQuantite_restante() %></td>
                    <td>
                        <a href="/TraitMatiere_premiere/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitMatiere_premiere/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                    </td>
                    <td><a href="/produits_concernes/<%= all[i].getId() %>">Voir</a></td>
                </tr>
                <% } %>
            </table>
            <center><nav><ul class="pagination pagination-lg"></ul></nav></center>
            <script src="/assets/myjs/pagination.js"></script>
        </div>
        <div class="col-md-1">
        </div>
        <div class="col-md-4">
            <form action="/InitMatiere_premiere" method="post">
                <input type="hidden" name="id" value="<%= currentMatiere_premiere != null ? currentMatiere_premiere.getId() : 0 %>">
                <% if (currentMatiere_premiere != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="nom">Nom</label>
                    <input type="text" name="nom" id="nom" class="form-control" value="<%= currentMatiere_premiere != null ? currentMatiere_premiere.getNom() : "" %>">
                </div>
                <div class="form-group">
                    <label for="unite">Unite</label>
                    <select name="unite" id="unite" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allUnite.length; j++) { %>
                        <option value="<%= allUnite[j].getId() %>" <%= currentMatiere_premiere != null && currentMatiere_premiere.getUnite() != null && currentMatiere_premiere.getUnite().getId() == allUnite[j].getId() ? "selected" : "" %>><%= allUnite[j].getNom() %>
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
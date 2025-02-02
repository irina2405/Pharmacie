<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
        <%
            Matiere_premiere[] all = (Matiere_premiere[]) request.getAttribute("all");
        %>
        <div class="row">
            <div class="col-md-2">
            </div>
            <div class="col-md-8 tableContainer ">
                <table class="table table-striped" id="table">
                    <tr>
                        <th>Id</th>
                        <th>Nom</th>
                        <th>Unite</th>
                        <th>Actions</th>
                        <th> </th>
                    </tr>
                    <% for (int i = 0; i < all.length; i++) { %>
                    <tr>
                        <td><%= all[i].getId() %></td>
                        <td><%= all[i].getNom() %></td>
                        <td><%= all[i].getUnite() != null ? all[i].getUnite().getNom() : "" %> </td>
                        <td>
                            <a href="/TraitMatiere_premiere/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                            <a href="/InitMatiere_premiere/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <center><nav><ul class="pagination pagination-lg"></ul></nav></center>
                <script src="/assets/myjs/pagination.js"></script>
            </div>
            <div class="col-md-2">
            </div>       
        </div>
    </div>
</body>
</html>
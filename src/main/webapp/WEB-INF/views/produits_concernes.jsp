<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
        <%
            Produit[] all = (Produit[]) request.getAttribute("all");
        %>
        <div class="row">
            <div class="col-md-2">
            </div>
            <div class="col-md-8 tableContainer ">
                <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Nom</th>
                    <th>Denorm prix vente</th>
                    <th>Min age</th>
                    <th>Max age</th>
                    <th>Unite</th>
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
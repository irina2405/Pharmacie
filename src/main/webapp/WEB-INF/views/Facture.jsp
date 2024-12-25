<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Facture[] all = (Facture[]) request.getAttribute("all");
        Facture currentFacture = (Facture) request.getAttribute("currentFacture");
        Client[] allClient = (Client[]) request.getAttribute("allClient");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Date </th>
                    <th>Total</th>
                    <th>Total paye</th>
                    <th>Client</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getTotal() %></td>
                    <td><%= all[i].getTotal_paye() %></td>
                    <td><%= all[i].getClient() != null ? all[i].getClient().getNom() : "" %> </td>
                    <td>
                        <a href="/TraitFacture/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitFacture/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
            <form action="/InitFacture" method="post">
                <input type="hidden" name="id" value="<%= currentFacture != null ? currentFacture.getId() : 0 %>">
                <% if (currentFacture != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="datetime-local" name="date_" id="date_" class="form-control" value="<%= currentFacture != null ? currentFacture.getDate_() : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>"  step="1">
                </div>
                <div class="form-group">
                    <label for="total">Total</label>
                    <input type="number" name="total" id="total" class="form-control" value="<%= currentFacture != null ? currentFacture.getTotal() : "" %>">
                </div>
                <div class="form-group">
                    <label for="total_paye">Total paye</label>
                    <input type="number" name="total_paye" id="total_paye" class="form-control" value="<%= currentFacture != null ? currentFacture.getTotal_paye() : "" %>">
                </div>
                <div class="form-group">
                    <label for="client">Client</label>
                    <select name="client" id="client" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allClient.length; j++) { %>
                        <option value="<%= allClient[j].getId() %>" <%= currentFacture != null && currentFacture.getClient() != null && currentFacture.getClient().getId() == allClient[j].getId() ? "selected" : "" %>><%= allClient[j].getNom() %>
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
<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Tresorerie[] all = (Tresorerie[]) request.getAttribute("all");
        Tresorerie currentTresorerie = (Tresorerie) request.getAttribute("currentTresorerie");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Motif</th>
                    <th>Date </th>
                    <th>Depot</th>
                    <th>Retrait</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getMotif() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getDepot() %></td>
                    <td><%= all[i].getRetrait() %></td>
                    <td>
                        <a href="/TraitTresorerie/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitTresorerie/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                    </td>
                </tr>
                <% } %>
            </table>
            <p>Solde : <%= Tresorerie.getSolde(null) %></p>
            <center><nav><ul class="pagination pagination-lg"></ul></nav></center>
            <script src="/assets/myjs/pagination.js"></script>
        </div>
        <div class="col-md-1">
        </div>
        <div class="col-md-4">
            <form action="/InitTresorerie" method="post">
                <input type="hidden" name="id" value="<%= currentTresorerie != null ? currentTresorerie.getId() : 0 %>">
                <% if (currentTresorerie != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="motif">Motif</label>
                    <input type="text" name="motif" id="motif" class="form-control" value="<%= currentTresorerie != null ? currentTresorerie.getMotif() : "" %>">
                </div>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="datetime-local" name="date_" id="date_" class="form-control" value="<%= currentTresorerie != null ? currentTresorerie.getDate_() : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>"  step="1">
                </div>
                <div class="form-group">
                    <label for="depot">Depot</label>
                    <input type="number" name="depot" id="depot" class="form-control" value="<%= currentTresorerie != null ? currentTresorerie.getDepot() : "" %>">
                </div>
                <div class="form-group">
                    <label for="retrait">Retrait</label>
                    <input type="number" name="retrait" id="retrait" class="form-control" value="<%= currentTresorerie != null ? currentTresorerie.getRetrait() : "" %>">
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Achat_mp[] all = (Achat_mp[]) request.getAttribute("all");
        Achat_mp currentAchat_mp = (Achat_mp) request.getAttribute("currentAchat_mp");
        Fournisseur_mp[] allFournisseur_mp = (Fournisseur_mp[]) request.getAttribute("allFournisseur_mp");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Date </th>
                    <th>Qt mp</th>
                    <th>Reste mp</th>   
                    <th>Fournisseur mp</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getQt_mp() %></td>
                    <td><%= all[i].getReste_mp() %></td>
                    <td><%= all[i].getFournisseur_mp() != null ? all[i].getFournisseur_mp().getId() + " -- " + all[i].getFournisseur_mp().getFournisseur().getNom() + " -- " + all[i].getFournisseur_mp().getMp().getNom() + " -- " + all[i].getFournisseur_mp().getPrix() : "" %> </td>
                    <td>
                        <%-- <a href="/TraitAchat_mp/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a> --%>
                        <%-- <a href="/InitAchat_mp/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a> --%>
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
            <form action="/InitAchat_mp" method="post">
                <input type="hidden" name="id" value="<%= currentAchat_mp != null ? currentAchat_mp.getId() : 0 %>">
                <% if (currentAchat_mp != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="datetime-local" name="date_" id="date_" class="form-control" value="<%= currentAchat_mp != null ? currentAchat_mp.getDate_() : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %>"  step="1">
                </div>
                <div class="form-group">
                    <label for="qt_mp">Qt mp</label>
                    <input type="number" name="qt_mp" id="qt_mp" class="form-control" value="<%= currentAchat_mp != null ? currentAchat_mp.getQt_mp() : "" %>">
                </div>
                <div class="form-group">
                    <label for="fournisseur_mp">Fournisseur mp</label>
                    <select name="fournisseur_mp" id="fournisseur_mp" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allFournisseur_mp.length; j++) { %>
                        <option value="<%= allFournisseur_mp[j].getId() %>" <%= currentAchat_mp != null && currentAchat_mp.getFournisseur_mp() != null && currentAchat_mp.getFournisseur_mp().getId() == allFournisseur_mp[j].getId() ? "selected" : "" %>><%= allFournisseur_mp[j].getId() + " -- " + allFournisseur_mp[j].getFournisseur().getNom() + " -- " + allFournisseur_mp[j].getMp().getNom()   %>
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
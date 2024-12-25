<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Achat_mp[] all = (Achat_mp[]) request.getAttribute("all");
        Achat_mp currentAchat_mp = (Achat_mp) request.getAttribute("currentAchat_mp");
        Fournisseur[] allFournisseur = (Fournisseur[]) request.getAttribute("allFournisseur");
        Matiere_premiere[] allMp = (Matiere_premiere[]) request.getAttribute("allMp");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Date </th>
                    <th>Qt mp</th>
                    <th>Denorm prix achat</th>
                    <th>Fournisseur</th>
                    <th>Matiere premiere</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td><%= all[i].getQt_mp() %></td>
                    <td><%= all[i].getDenorm_prix_achat() %></td>
                    <td><%= all[i].getFournisseur() != null ? all[i].getFournisseur().getNom() : "" %> </td>
                    <td><%= all[i].getMp() != null ? all[i].getMp().getNom() : "" %> </td>
                    <td>
                        <a href="/TraitAchat_mp/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitAchat_mp/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
                    <label for="denorm_prix_achat">Denorm prix achat</label>
                    <input type="number" name="denorm_prix_achat" id="denorm_prix_achat" class="form-control" value="<%= currentAchat_mp != null ? currentAchat_mp.getDenorm_prix_achat() : "" %>">
                </div>
                <div class="form-group">
                    <label for="fournisseur">Fournisseur</label>
                    <select name="fournisseur" id="fournisseur" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allFournisseur.length; j++) { %>
                        <option value="<%= allFournisseur[j].getId() %>" <%= currentAchat_mp != null && currentAchat_mp.getFournisseur() != null && currentAchat_mp.getFournisseur().getId() == allFournisseur[j].getId() ? "selected" : "" %>><%= allFournisseur[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="mp">Matiere premiere</label>
                    <select name="mp" id="mp" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allMp.length; j++) { %>
                        <option value="<%= allMp[j].getId() %>" <%= currentAchat_mp != null && currentAchat_mp.getMp() != null && currentAchat_mp.getMp().getId() == allMp[j].getId() ? "selected" : "" %>><%= allMp[j].getNom() %>
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
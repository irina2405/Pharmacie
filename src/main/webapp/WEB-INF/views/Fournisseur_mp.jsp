<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<html>
<body>
    <div class="container">
    <%
        Fournisseur_mp[] all = (Fournisseur_mp[]) request.getAttribute("all");
        Fournisseur_mp currentFournisseur_mp = (Fournisseur_mp) request.getAttribute("currentFournisseur_mp");
        Matiere_premiere[] allMp = (Matiere_premiere[]) request.getAttribute("allMp");
        Fournisseur[] allFournisseur = (Fournisseur[]) request.getAttribute("allFournisseur");
    %>
    <div class="row">
        <div class="col-md-7 tableContainer ">
            <table class="table table-striped" id="table">
                <tr>
                    <th>Id</th>
                    <th>Matiere premiere</th>
                    <th>Fournisseur</th>
                    <th>Prix</th>
                    <th>Date </th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
                <% for (int i = 0; i < all.length; i++) { %>
                <tr>
                    <td><%= all[i].getId() %></td>
                    <td><%= all[i].getMp() != null ? all[i].getMp().getNom() : "" %> </td>
                    <td><%= all[i].getFournisseur() != null ? all[i].getFournisseur().getNom() : "" %> </td>
                    <td><%= all[i].getPrix() %></td>
                    <td><%= all[i].getDate_() %></td>
                    <td>
                        <a href="/TraitFournisseur_mp/<%= all[i].getId() %>" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        <a href="/InitFournisseur_mp/delete/<%= all[i].getId() %>" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
            <form action="/InitFournisseur_mp" method="post">
                <input type="hidden" name="id" value="<%= currentFournisseur_mp != null ? currentFournisseur_mp.getId() : 0 %>">
                <% if (currentFournisseur_mp != null) { %>
                    <input type="hidden" name="mode" value="u">
                <% } %>
                <div class="form-group">
                    <label for="mp">Matiere premiere</label>
                    <select name="mp" id="mp" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allMp.length; j++) { %>
                        <option value="<%= allMp[j].getId() %>" <%= currentFournisseur_mp != null && currentFournisseur_mp.getMp() != null && currentFournisseur_mp.getMp().getId() == allMp[j].getId() ? "selected" : "" %>><%= allMp[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="fournisseur">Fournisseur</label>
                    <select name="fournisseur" id="fournisseur" class="form-control">
                        <option value="">-- choose --</option>
                        <% for (int j = 0; j < allFournisseur.length; j++) { %>
                        <option value="<%= allFournisseur[j].getId() %>" <%= currentFournisseur_mp != null && currentFournisseur_mp.getFournisseur() != null && currentFournisseur_mp.getFournisseur().getId() == allFournisseur[j].getId() ? "selected" : "" %>><%= allFournisseur[j].getNom() %>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="prix">Prix</label>
                    <input type="number" name="prix" id="prix" class="form-control" value="<%= currentFournisseur_mp != null ? currentFournisseur_mp.getPrix() : "" %>">
                </div>
                <div class="form-group">
                    <label for="date_">Date </label>
                    <input type="date" name="date_" id="date_" class="form-control" value="<%= currentFournisseur_mp != null ? currentFournisseur_mp.getDate_() : "" %>">
                </div>
                <button type="submit" class="btn btn-default">Valider</button>
            </form>
        </div>
</div>
    </div>

</body>
</html>
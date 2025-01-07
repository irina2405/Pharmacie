<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<body>

    <h1>Donnees de la vue : V_depense_mp_sur_produit</h1>
    <table class="table">
        <thead>
            <tr>
                <th>Id_mp</th>
                <th>Id_produit</th>
                <th>Qt_produit</th>
                <th>Qt_mp</th>
                <th>Date_</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<V_depense_mp_sur_produit> data = (List<V_depense_mp_sur_produit>) request.getAttribute("V_depense_mp_sur_produit");
                for (V_depense_mp_sur_produit item : data) {
            %>
            <tr>
                <td><%= item.getId_mp() %></td>
                <td><%= item.getId_produit() %></td>
                <td><%= item.getQt_produit() %></td>
                <td><%= item.getQt_mp() %></td>
                <td><%= item.getDate_() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
<%@ include file="/WEB-INF/views/templates/footer.jsp" %>

<%@ include file="/WEB-INF/views/templates/header.jsp" %>
<body>

    <h1>Donnees de la vue : V_facture_impaye</h1>
    <table class="table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Date_</th>
                <th>Total</th>
                <th>Total_paye</th>
                <th>Id_client</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<V_facture_impaye> data = (List<V_facture_impaye>) request.getAttribute("V_facture_impaye");
                for (V_facture_impaye item : data) {
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getDate_() %></td>
                <td><%= item.getTotal() %></td>
                <td><%= item.getTotal_paye() %></td>
                <td><%= Client.getById(item.getId_client()).getNom() %></td>
                <td><a href="/payer_en_totalite/<%= item.getId() %>">payer en totalite</a></td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
<%@ include file="/WEB-INF/views/templates/footer.jsp" %>

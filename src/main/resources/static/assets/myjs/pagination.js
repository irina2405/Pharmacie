const platPerPage = 8;
    function paginateTable(tableId, currentPage) {
        // Example: Hide all rows, then show rows for the current page
        var table = document.getElementById(tableId);
        var rows = table.getElementsByTagName('tr');

        for (var i = 1; i < rows.length; i++) {
            rows[i].style.display = 'none';
        }

        var startIndex = (currentPage - 1) * platPerPage;
        var endIndex = Math.min(startIndex + platPerPage, rows.length);

        for (var i = startIndex + 1; i < endIndex + 1 && i < rows.length; i++) {
            rows[i].style.display = '';
        }
    }
    function afficherPagination(tableId) {
        var table = document.getElementById(tableId);
        var rows = table.getElementsByTagName('tr');
        const pagination = document.getElementsByClassName('pagination pagination-lg')[0];
    
        // Calcul du nombre total de pages
        const totalPages = Math.ceil(rows.length / platPerPage);
    
        // Nettoyer la pagination existante avant de la recréer
        pagination.innerHTML = '';
    
        for (let index = 1; index <= totalPages; index++) {
            const li = document.createElement('li');
            const link = document.createElement('a');
            link.textContent = index;
            li.appendChild(link);
            li.addEventListener('click', () => {
                paginateTable(tableId, index);
            });
            pagination.appendChild(li);
        }
    }
    
paginateTable("table", 1);
afficherPagination("table");
    
    

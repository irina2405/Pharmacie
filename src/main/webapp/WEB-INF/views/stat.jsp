<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistiques</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <input id="date_start" type="datetime-local" name="date_start">
    <input id="date_end" type="datetime-local" name="date_end">
    <button id="btn">Valider</button>

    <h1>Graphique des Statistiques</h1>
    <canvas id="chart" width="400" height="200"></canvas>

    <script>
        window.addEventListener("load", function () {
            const btn = document.getElementById("btn");

            btn.addEventListener("click", function () {
                // Récupération des valeurs des champs
                let dateStart = document.getElementById("date_start").value;
                let dateEnd = document.getElementById("date_end").value;

                console.log("click");
                console.log(dateStart);
                console.log(dateEnd);
                let url = "/statVente?date_start="+encodeURIComponent(dateStart)+"&date_end="+encodeURIComponent(dateEnd);
                console.log(url);
                // Envoi des valeurs au contrôleur via une requête GET
                fetch(url)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Erreur lors de la requête');
                        }
                        return response.json();
                    })
                    .then(data => {
                        // Affichage des données dans un graphique Chart.js
                        const ctx = document.getElementById('chart').getContext('2d');
                        new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: data.suivantX, // Labels pour l'axe X
                                datasets: [{
                                    label: 'Valeurs Y',
                                    data: data.suivantY, // Données pour l'axe Y
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    borderWidth: 2,
                                    fill: false
                                }]
                            },
                            options: {
                                scales: {
                                    x: {
                                        title: {
                                            display: true,
                                            text: 'Axe X'
                                        }
                                    },
                                    y: {
                                        title: {
                                            display: true,
                                            text: 'Axe Y'
                                        }
                                    }
                                }
                            }
                        });
                    })
                    .catch(error => console.error('Erreur lors de la récupération des statistiques:', error));
            });
        });
    </script>
</body>
</html>

let cellsForChange = document.getElementById('myTable').rows[1].cells;

for(let i = 0; i < cellsForChange.length; i++) {
    cellsForChange[i].innerHTML = "changed" + i;
}
function appendToVersionsList(listId) {

    let list = document.getElementById(listId);

    versions.forEach((item) => {
        let li = document.createElement("li");
        let a = document.createElement('a');
        a.setAttribute('href', item);
        a.innerText = item;
        li.appendChild(a);
        list.appendChild(li);
    });

}
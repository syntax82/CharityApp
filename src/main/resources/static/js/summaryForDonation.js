let array = document.getElementsByClassName("example");

let button = document.getElementById("lastButton");

button.addEventListener("click", function (){

    /// firstRow

    let checked = [];
    let chekedText = [];
    for (let i = 0; i < array.length; i++) {
        if(array.item(i).checked===true) checked.push(array.item(i))
    }
    for (let i = 0; i < checked.length; i++) {
        chekedText.push(checked[i].parentElement.querySelector(".categoryName").innerHTML);
    }
    let quantityOfBags = document.querySelector("#quantityOfBags").value;
    let firstRow = quantityOfBags + ' worki z : ';
    for (let i = 0; i < chekedText.length; i++) {
        firstRow=firstRow+chekedText[i];
        if(i<(chekedText.length-1)) firstRow+=", "
    }
    document.getElementById('summary1').innerText=firstRow;

    //// second row

    let organization = document.getElementsByClassName("radioValues");
    let checkedOrganization;
    for (let i = 0; i < organization.length; i++) {
        if(organization.item(i).checked===true) checkedOrganization=organization.item(i).parentElement.querySelector(".title").innerText;
    }
    document.getElementById("summary2").innerText="Dla fundacji \"" + checkedOrganization + "\"";


    //// third row

    let street = document.getElementById('street').value;
    let city = document.getElementById('city').value;
    let postcode = document.getElementById('postcode').value;
    let phone = document.getElementById('phone').value;

    document.getElementById('streetS').innerText=street;
    document.getElementById('cityS').innerText=city;
    document.getElementById('postcodeS').innerText=postcode;
    document.getElementById('phoneS').innerText=phone;


    //// last row

    let date = document.getElementById('date').value;
    let time = document.getElementById('time').value;
    let comment = document.getElementById('comment').value;

    document.getElementById('dateS').innerText=date;
    document.getElementById('timeS').innerText=time;
    document.getElementById('commentS').innerText=comment;


})
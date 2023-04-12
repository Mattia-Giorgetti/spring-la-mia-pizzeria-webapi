// VARIABILI 

const BASE_URL = 'http://localhost:8080/api/pizzas';
const pizzaList = document.getElementById('pizza-list');
const newPizzaForm = document.getElementById('pizza-form');


// CHIAMATE API 

const getPizzas = async ()=>{
    const res = await fetch(BASE_URL);
    return res;
};

const createPizza = async (jsonData) => {
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonData,
    }
    const res = await fetch(BASE_URL, options);
    return res;
};

const deleteById = async (id)=>{
    const res = await fetch(`${BASE_URL}/${id}`, {method: 'DELETE'});
    return res;
};
// ELEMENTI DOM
const renderDeleteButton = (pizza)=>{
    let btn = '';
    const activeOffers = pizza.offers;
    // console.log(activeOffers)
    if(activeOffers !== null && activeOffers.length > 0){
        btn = `<button class="d-none">Da disattivare</button>`;
    } else {
        btn = `<button data-id="${pizza.id}" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i></button>`
    }
    return btn;
};

const createCardItem = (item) => {
    return `<div class="card mb-3" style="max-width: 540px;">
    <div class="row g-0">
      <div class="col-md-4 ">
        <img src="${item.image}" class="img-fluid rounded-start" alt="${item.name}">
      </div>
      <div class="col-md-8">
        <div class="card-body">
          <h5 class="card-title">${item.name}</h5>
          <p class="card-text">${item.description}</p>
          <p class="card-text"><small class="text-body-secondary">€${item.price}</small></p>
          <div>${renderDeleteButton(item)}</div> 
        </div>
      </div>
    </div>
  </div>`;
};

const createPizzaList  = (data)=>{
    let pizzaList = `<div class="row gap-4">`;
    data.forEach(element => {
        pizzaList += createCardItem(element);
    });
    pizzaList += `</div>`;
    return pizzaList;
}

// FUNZIONI GENERICHE 

const fetchData = async ()=>{
    const res = await getPizzas();
    // console.log(res);
    if(res.ok){
        const data = await res.json();
        pizzaList.innerHTML = createPizzaList(data);
    const deleteBtns = document.querySelectorAll('button[data-id]');
    for(let btn of deleteBtns){
        btn.addEventListener('click', ()=>{
            deletePizza(btn.dataset.id);
        });
    }
} else {
    console.log('Error')
}
};

const savePizza = async (e)=>{
    e.preventDefault();
    const formData = new FormData(e.target);
    const formDataObj = Object.fromEntries(formData.entries());
    const formDataJson = JSON.stringify(formDataObj);
    const res = await createPizza(formDataJson);
    const responseBody = await res.json();

    if(res.ok){
        fetchData();
        e.target.reset();
    } else {
        console.log(res.status);
        console.log(responseBody);
    }
}

const deletePizza = async (pizzaId)=> {
    const res = await deleteById(pizzaId);
    if(res.ok){
        fetchData();
    } else {
        console.log(res.status);
    }
};



// INVOCO FUNZIONI 

newPizzaForm.addEventListener('submit', savePizza);
fetchData();
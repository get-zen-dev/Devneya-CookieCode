import {generateCode} from './lib/api.js';

const formSubmit = document.getElementById('submit-form')

const response = document.getElementById('response')

formSubmit.addEventListener("submit", function(event){
    response.value = "";
    event.preventDefault();

    let apiKey = document.getElementById("api-key").value;
    let prompt = document.getElementById("prompt").value;

    (async () => {
        let data = await generateCode(apiKey, 3, prompt)
        let res = JSON.parse(JSON.stringify(data))
        response.value = res
    })();

})
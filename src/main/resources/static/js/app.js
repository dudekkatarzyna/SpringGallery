function deletePicture(id) {
    axios.delete(`/gallery/pictures/${id}`)
        .then(function (response) {
            console.log(response.data.result);

            if (response.data.result) {
                const col = document.querySelector(`#id${id}`);
                col.classList.add('d-none');
            }

        })
        .catch(function (error) {
            console.log(error);
        });

}

function checkLogin(name, password) {
    return name.equalsIgnoreCase("admin") && password.equals("admin");

}

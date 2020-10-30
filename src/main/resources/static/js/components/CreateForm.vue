<template>
    <div>
        <form name="creationForm" method="post" action="">

            <input type="text" name="name" placeholder="Enter the form name"/>
            <button type="submit">Добавить</button>
            <input type="text" name="question" placeholder="Enter the question" style="display: block"/>
            <div v-on:input="addAnswer" id="answersContainer">
                <input type="text" name="answers" placeholder="Enter answers" ></input>
            </div>
            <p> Множественный выбор:
                <input type="checkbox" name="multipleChoice" value="multiple"/>
            </p>
        </form>
    </div>
</template>

<script>
    export default {
        name: 'CreateForm',
        props: {
            msg: String
        },
        methods: {
            addAnswer: function (event) {
                if(event.target==answersContainer.lastChild) {
                    let newField = document.createElement("input");
                    newField.type = "text";
                    newField.name = "answers";
                    newField.style.display = "block";
                    answersContainer.appendChild(newField);
                }

            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

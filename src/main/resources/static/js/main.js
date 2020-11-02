let createFormApi = Vue.resource('/createForm/forms');
let viewFormApi = Vue.resource('/viewForm/forms');
let fillFormApi = Vue.resource('/fillForm/forms');
let userEditApi = Vue.resource('/userEdit/forms');

Vue.component('userEdit', {
    data() {
        return {
            users: "",
            role: [],
            admin: "ADMIN"
        }
    },
    methods: {
      isAdmin(username, id) {
          if(this.role[id]){username.roles[0]="ADMIN"}
          else {username.roles[0]="USER"}
          this.users[id-1]=username
      },
        submitRole() {
            userEditApi.update({}, this.users)
        },
        checked: function (userRole) {
            return userRole==="ADMIN"
      }
    },
    created() {
        userEditApi.get('/forms').then(result =>
            result.json().then( data =>  this.users=data))
    },
    template:   '<div>' +
                    '<table>' +
                        '<thead>' +
                            '<tr>' +
                                '<th>Имя</th>' +
                                '<th>Роль</th>' +
                            '</tr>' +
                        '</thead>' +
                        '<tbody>' +
                            '<tr v-for="username in users" :key="username.id" >' +
                                '<td> {{username.username}}</td>' +
                                '<td > {{username.roles[0]}}</td>' +
                                '<td><input type="checkbox" :checked="checked(username.roles[0])" :value="true" v-model="role[username.id]" ' +
                                    '@change="isAdmin(username, username.id)"/>Присвоить права администратора</td>' +
                            ' </tr>' +
                        '</tbody>' +
                    '</table>' +
        '<button type="button" @click="submitRole">Сохранить</button>' +
                '</div>'
})

Vue.component('fillForm', {
    data() {
        return {
            viewForms : "",
        }
    },
    created() {
        fillFormApi.get('/forms').then(result =>
            result.json().then( data =>  this.viewForms=data))
    },

    template:  '<div class="container row">' +
                    '<div class="border rounded col-sm-4 ml-3" v-for="(form, index) in viewForms" :key="form.id"  >' +
                        '<div class="my-2">{{form.author.username}}</div>' +
                        '<div class="mb-4 mx-2 h5">{{form.formName}}</div>' +
                        '<div v-for="(question, index) in form.questions" :key="question.id"  >' +
                            '<div class="my-3 mx-2 h6">{{question.question}}</div>' +
                                '<div v-for="(answer, index) in question.answers" :key="answer.id" ' +
                                    'class="form-check border rounded mb-1 mx-2" >' +
                                    '<input v-if="question.multiple==true" type="checkbox"' +
                                        ' v-model="answer.filled" disabled/>' +
                                    '<input v-else type="radio" :name="question.question+question.id"' +
                                        ':checked="answer.filled" disabled />' +
                                    '<label class="form-check-label ml-1 text-success" ' +
                                        'v-if="answer.filled==true" >{{answer.answer}}</label>' +
                                    '<label class="form-check-label ml-1" ' +
                                        'v-else >{{answer.answer}}</label>' +
                                '</div>' +
                        '</div>' +
                    '</div>' +
                '</div>'
})

Vue.component('viewForm', {
    data() {
        return {
            previousRadioButton: null,
            previousName: null,
            viewForms : "",
            saveForm: "",
        }
    },
    created() {
        viewFormApi.get('forms').then(result =>
            result.json().then( data =>  this.viewForms=data))
    },
    methods: {
        isFilled(answer, name, answers) {
            if (this.previousRadioButton) {
                if (name === this.previousName) {
                    this.previousRadioButton.filled = !this.previousRadioButton.filled
                }
                else {
                    for (ans of answers) {
                        ans.filled = false
                    }
                }
            }
            else{
                for (ans of answers) {
                    ans.filled = false
                }
            }
            this.previousRadioButton = answer
            this.previousName = name
            answer.filled = !answer.filled
        },
        sendForm(form) {
            viewFormApi.update({}, form)
        }
    },
    template: '<div>'+
              '<div class="container row">' +
                    '<div class="border rounded col-sm-4 ml-3" v-for="(form, index) in viewForms" :key="form.id"  >' +
                        '<div class="my-4 mx-2 h5">{{form.formName}}</div>' +
                        '<div v-for="(question, index) in form.questions" :key="question.id"  >' +
                            '<div class="my-3 mx-2 h6">{{question.question}}</div> ' +
                            '<div class="form-check border rounded mb-1 mx-2" ' +
                                'v-for="(answer, index) in question.answers" :key="answer.id"  >' +
                                '<input class="form-check-input" v-if="question.multiple==true" ' +
                                    'type="checkbox" v-model="answer.filled"/>' +
                                '<input v-else class="form-check-input" type="radio" ' +
                                    ':name="question.question+question.id"' +
                                    ':value="question.question+answer.id" ' +
                                    '@change="isFilled(answer, question.question+question.id, question.answers)"' +
                                    ':checked="answer.filled"/>' +
                                '<label class="form-check-label">{{answer.answer}}</label>' +
                            '</div>' +
                        '</div>' +
                        '<button class="btn btn-primary ml-2" type="button" @click="sendForm(form)">Отправить данные</button>' +
                    '</div>' +
              '</div>'+
              '</div>'
})

Vue.component('createAnswer', {
    props: ["answerIndex"],
    data() {
        return {
            answer: "",
            internalAnswerIndex: this.answerIndex,
        }
    },
    methods: {
        answersReturn() {
            this.$emit("answersReturn", this.answer, this.internalAnswerIndex);
        },
    },
    template:
        '<input class="form-control col-sm-3 mx-2" type="text" placeholder="введите вариант ответа" ' +
            'style="display: block" ' +
            'v-on:change="answersReturn" v-model.lazy="answer"/> '
})

Vue.component('createQuestion', {
    props:['questionIndex'],
    data() {
        return {
            question: "",
            answerIndex: 0,
            answer: "",
            multiple: false,
            answersArray: [{answer: ""}],
            internalQuestionIndex : this.questionIndex,
        }
    },
    methods: {
        addAnswer () {
            const parent = event.target.parentNode;
            if (event.target === parent.lastChild) {
                this.answersArray.push({answer: ""})
                this.answerIndex++
            }
        },
        questionReturn() {
            this.$emit("questionReturn",this.question, this.internalQuestionIndex)
        },
        answersReturn(answer, answerIndex) {
            this.$emit("answersReturn", answer, this.internalQuestionIndex,
                answerIndex)
        },
        multipleAnswers(){this.$emit("multipleAnswers", this.multiple, this.internalQuestionIndex)}

    },
    template:
            '<div>\n' +
                '<input class="form-control col-sm-3 my-3 mx-2" type="text"  placeholder="Введите вопрос"' +
                    'v-model.lazy="question"' +
                    '@change="questionReturn"/>' +
                '<div @input="addAnswer"> ' +
                '<create-answer ' +
                    'v-for="(answer, index) in answersArray" :key="index" ' +
                    ':answerIndex="index"' +
                    '@answersReturn="answersReturn"/>' +
                '</div> ' +
                '<div class="form-check my-2">  ' +
                    '<label class="form-check-label"> Множественный выбор  </label>' +
                    '<input class="form-check-input ml-3" type="checkbox" name="multipleChoice" v-model="multiple" ' +
                            '@change="multipleAnswers"/>\n' +
                '</div>' +
            '</div>'
})

Vue.component('createForm', {
    data: function() {
            return {
            index: 0,
            questionIndex: 0,
            answerIndex: 0,
            forms: {
                formName:"",
                questions : [{
                    question: "",
                    multiple: false,
                    answers: [{
                        answer: "",
                        filled: false,
                    }],
                }],
            },
        }
    },
    methods: {
        multipleAnswers(multiple, questionindex){
            this.forms.questions[questionindex].multiple = multiple
        },
        questionBack(questionReturn, questionindex) {
            this.forms.questions[questionindex].question = questionReturn
        },
        answersBack(answersReturn, questionindex, answerIndex) {
            if(this.forms.questions[questionindex].answers.length===answerIndex) {
                this.forms.questions[questionindex].answers.push({answer: answersReturn})
            }
            this.forms.questions[questionindex].answers[answerIndex].answer=answersReturn
        },
        addQuestion: function () {
            this.forms.questions.push({
                                question: "",
                                answers: [{answer:""}]});
            this.questionIndex++;
            this.answerIndex = 0;
//            this.questionArray.push("")
        },
        addForm: function () {
            createFormApi.save({},this.forms)
        }
    },
    template: '<div class="form-group ml-3">\n' +
        '        <div>\n' +
        '            <input class="form-control col-sm-3 my-4 mx-2" type="text" placeholder="Введите название анкеты"' +
                        'v-model.lazy="forms.formName"/> ' +
        '            <create-question ' +
'                       v-for="(question, index) in forms.questions" ' +
    '                   :key="index"' +
    '                   :questionIndex="index"' +
'                       @questionReturn="questionBack"' +
    '                   @answersReturn="answersBack"' +
    '                   @multipleAnswers="multipleAnswers"/>' +
        '            <button class="btn btn-primary" @click="addQuestion" type="button">Добавить вопрос</button>\n' +
        '            <button class="btn btn-primary" type="button" @click="addForm">Сохранить анкету</button>' +
        '        </div>\n' +
        '    </div>'
})

var app = new Vue({
    el: '#app',

});


var students = [
  {name:"Robert Stone", university:"Little Explorers - Nursery School", logo:"litle-explorers.png"},
  {name:"Robert Stone", university:"University of California", logo:"university-of-california.png"},
  {name:"Robert Miles", university:"University of California", logo:"university-of-california.png"},
  {name:"Robert Stone", university:"University of California", logo:"university-of-california.png"},
  {name:"Robert Stone", university:"University of California", logo:"university-of-california.png"},
  {name:"Robert Stone", university:"University of California", logo:"university-of-california.png"}];

var schools = [
  {id:1, name:"Little Explorers - Nursery School", adress:"125 Maiden Lane, New York, NY 10038", level:2},
  {id:2, name:"University of California", adress:"125 Maiden Lane, New York, NY 10038", level:2},
  {id:3, name:"University of California", adress:"125 Maiden Lane, New York, NY 10038", level:3},
  {id:4, name:"University of California", adress:"125 Maiden Lane, New York, NY 10038", level:2},
  {id:5, name:"University of California", adress:"125 Maiden Lane, New York, NY 10038", level:3},
  {id:6, name:"University of California", adress:"125 Maiden Lane, New York, NY 10038", level:1}];

var token = "5fsf4g%$fgdgdjh58444121dfffKd"; // FAKE TOKEN FOR TESTING
var debug = 'true';

$(document).ready(function() {

  formsFunctional();
  searchStudent();
  searchSchool();
  pasteStudentName();
  entryAnimations();

  if($('#signin-form').length){
    signInValidation();
  }
  if($('#profile-form').length){
    profileValidation();
  }
  if($('#register-form').length){
    registerValidation();
  }
  if($('#enrollment-form').length){
    enrollmentValidation();
  }
  if($('#pre-register-form').length){
    preValidation();
  }

});

function entryAnimations(){
  window.onload = function () {
    $("body").addClass("play");

    setTimeout(function() {
      $("body").removeClass("entry");
    }, 1000)
  }
}

function signin(data) {
  setCookie('token', data);
  window.open('profile.html', '_self');
}

function auth(){

  if(!debug){

  token = getCookie('token');

  if(token){

  $.ajax({
   url: "auth.php",
   type: 'POST',
   data: {'token':token},
   success: function (data) {
     if(!data){window.open('signin.html', '_self');}
   },
   error: function (data) {
    window.open('signin.html', '_self');
    }
  });
  }else{
    window.open('signin.html', '_self');
  }
}
}

function pasteStudentName() {
  $("#studentName").html(getParameterByName('n'));
}

function formsFunctional() {

  $(".numeric .icon-top").on('click', function() {
    var i = Number($(".numeric input").val());
    $(".numeric input").val(i+1);
    $(".numeric input").trigger("input");
   });

  $(".numeric .icon-down").on('click', function() {
     var i = Number($(".numeric input").val());
     if(i>0){
     $(".numeric input").val(i-1);
     $(".numeric input").trigger("input");
    }
  });

  $(".icon-eye").on('click', function() {

    if($(this).hasClass('visible')){
      $('.icon-eye').removeClass('visible');
      $('input[name="password"]').attr('type', 'password');
      $('input[name="confirmPassword"]').attr('type', 'password');
    }else{
      $('.icon-eye').addClass('visible');
      $('input[name="password"]').attr('type', 'text');
      $('input[name="confirmPassword"]').attr('type', 'text');
    }

  });

}

function searchSchool(){

  $("#search-school input[name='school']").on('change', function (e) {

    var form = $("#search-school");

    form.find(".more").on('click', function(){
      $(".search-result").html(buildSchoolList(schools, students.length));
      form.find(".search-result-more").removeClass('visible');
      initEvents(form);
    });

    form.find(".icon-load").removeClass('d-none').addClass('d-block');
    form.find(".icon-search").removeClass('d-block').removeClass('green').addClass('d-none');

    setTimeout(function() {

      if (e.target.value != ''){

        if (e.target.value == 'Little'){

          if(students.length > 3) {form.find(".search-result-more").addClass('visible');}

          $(".search-result").html(buildSchoolList(schools, 3));
          initEvents(form);

          form.find(".icon-load").removeClass('d-block').addClass('d-none');
          form.find(".icon-search").removeClass('d-none').addClass('d-block').addClass('green');
          form.find(".search-empty").removeClass('visible');
          form.find(".search-result").addClass('visible');

        }else{

          form.find(".search-result-more").removeClass('visible');
          form.find(".icon-load").removeClass('d-block').addClass('d-none');
          form.find(".icon-search").removeClass('d-none').addClass('d-block').addClass('green');
          form.find(".search-result").removeClass('visible');
          form.find(".search-empty").addClass('visible');

        }
      }else{
        form.find(".search-result-more").removeClass('visible');
        form.find(".icon-load").removeClass('d-block').addClass('d-none');
        form.find(".icon-search").removeClass('d-none').addClass('d-block');
        form.find(".search-empty").removeClass('visible');
        form.find(".search-result").removeClass('visible');

      }


    }, 1000);

  });

  function initEvents(form) {

    form.find(".sch-id").on('click', function(e){
      $("input[name='id']").val($(this).attr('id'));
      $("input[name='adress']").val($(this).find('.adress').text());
      $("input[name='level']").val($(this).find('.level').text());
      $("#search-school input[name='school']").val($(this).find('.name').text());
      form.find(".search-result-more").removeClass('visible');
      form.find(".icon-load").removeClass('d-block').addClass('d-none');
      form.find(".icon-search").removeClass('d-none').addClass('d-block');
      form.find(".search-empty").removeClass('visible');
      form.find(".search-result").removeClass('visible');
      setParamsAndGo('q-new-year.html', ['level', 'school', 'adress', 'id'])
    });

  }
}

function searchStudent(){

  $("#search-childs input[name='child']").on('change', function (e) {

    var form = $("#search-childs");

    form.find(".more").on('click', function(){
      $(".search-result").html(buildStudentList(students, students.length));
      form.find(".search-result-more").removeClass('visible');
    });

    form.find(".icon-load").removeClass('d-none').addClass('d-block');
    form.find(".icon-search").removeClass('d-block').removeClass('green').addClass('d-none');

    setTimeout(function() {

      if (e.target.value != ''){

        if (e.target.value == 'Robert'){

          if(students.length > 3) {form.find(".search-result-more").addClass('visible');}
          $(".search-result").html(buildStudentList(students, 3));


          form.find(".icon-load").removeClass('d-block').addClass('d-none');
          form.find(".icon-search").removeClass('d-none').addClass('d-block').addClass('green');
          form.find(".search-empty").removeClass('visible');
          form.find(".search-result").addClass('visible');

        }else{

          form.find(".search-result-more").removeClass('visible');
          form.find(".icon-load").removeClass('d-block').addClass('d-none');
          form.find(".icon-search").removeClass('d-none').addClass('d-block').addClass('green');
          form.find(".search-result").removeClass('visible');
          form.find(".search-empty").addClass('visible');

        }
      }else{
        form.find(".search-result-more").removeClass('visible');
        form.find(".icon-load").removeClass('d-block').addClass('d-none');
        form.find(".icon-search").removeClass('d-none').addClass('d-block');
        form.find(".search-empty").removeClass('visible');
        form.find(".search-result").removeClass('visible');

      }

    }, 1000);

  });
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function buildStudentList(std, limit) {

  var html = '';

  std.slice(0, limit).forEach(function(element) {
      html += `<div class="row" onclick="setParamsAndGo('q-found.html', ['name'], '${element.name}')">
                <div class="col-md-4 d-flex align-items-center"><i class="icon-student"></i><span>Name<br><strong>${element.name}</strong></span></div>
                <div class="col-md-4 d-flex align-items-center"><span class="school">School<br><strong>${element.university}</strong></span></div>
                <div class="col-md-4 d-flex align-items-center text-right"><img src="img/${element.logo}" alt=""></div>
              </div>`;
  });

  return html;

}

function getApiParams (action, params){

  $.ajax({
   url: action,
   type: 'POST',
   data: params,
   success: function (data) {
     data.forEach(function(element, key) {
       $("#"+key).text(element);
     });
   },
   error: function (data) {
    console.log("Error: "+data);
    }
  });

}

function getParams(params) {

  params.forEach(function(element) {
    $("#"+element).text(getCookie(element));
  });

}

function setParamsAndGo (url, params, directly){

  if(directly){
    setCookie(params[0], directly);
  }else{
    params.forEach(function(element) {
      setCookie(element, $("input[name='"+element+"']").val());
    });
  }
  window.open(url, '_self');

}

function buildSchoolList(sch, limit) {

  var html = '';

  sch.slice(0, limit).forEach(function(element) {
      html += `<div class="row sch-id" id="${element.id}">
                <div class="col-md-5 d-flex align-items-center"><span>Name<br><strong class="name">${element.name}</strong></span></div>
                <div class="col-md-5 d-flex align-items-center"><span>Adress<br><strong class="adress">${element.adress}</strong></span></div>
                <div class="col-md-2 d-flex align-items-center"><span>Grade Level<br><strong class="level">${element.level}</strong></span></div>
              </div>`;
  });

  return html;

}

function getCookie(name) {
  var matches = document.cookie.match(new RegExp(
    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
  ));
  return matches ? decodeURIComponent(matches[1]) : undefined;
}

function deleteCookie(name) {
  setCookie(name, "", {
    expires: -1
  })
}

function setCookie(name, value, options) {
  options = options || {};

  var expires = options.expires;

  if (typeof expires == "number" && expires) {
    var d = new Date();
    d.setTime(d.getTime() + expires * 1000);
    expires = options.expires = d;
  }
  if (expires && expires.toUTCString) {
    options.expires = expires.toUTCString();
  }

  value = encodeURIComponent(value);

  var updatedCookie = name + "=" + value;

  for (var propName in options) {
    updatedCookie += "; " + propName;
    var propValue = options[propName];
    if (propValue !== true) {
      updatedCookie += "=" + propValue;
    }
  }

  document.cookie = updatedCookie;
}

function registerValidation(){

  $('#register-form').bootstrapValidator({
    fields: {
      email: {
        validators: {
          notEmpty: {
            message: 'Please supply your email address'
          },
          emailAddress: {
            message: 'Please supply a valid email address'
          }
        }
      },
      name: {
        validators: {
          notEmpty: {
            message: 'The Full Name is required'
          },
          regexp: {
            regexp: /^[a-zA-Z ]+$/,
            message: 'Your Full Name cannot have numbers or symbols'
          }
        }
      },
      password: {
        validators: {
          identical: {
            field: 'confirmPassword',
            message: 'The password and its confirm are not the same'
          }
        }
      },
      confirmPassword: {
        validators: {
          identical: {
            field: 'password',
            message: 'The password and its confirm are not the same'
          }
        }
      }
    }

  }).on('success.form.bv', function(e) {

    $('#register-form').data('bootstrapValidator').resetForm();

    e.preventDefault();

    var $form = $(e.target);
    var bv = $form.data('bootstrapValidator');

    $.ajax({
     url: $form.attr('action'),
     type: 'POST',
     data: $form.serialize(),
     success: function (data) {
      window.open('signin.html', '_self')
     },
     error: function (data) {
      console.log("Error: "+data);
      if(debug){window.open('signin.html', '_self')}
      }
    });

  });
}

function enrollmentValidation(){

  $('#enrollment-form').bootstrapValidator({
    fields: {
      region: {
        validators: {
          notEmpty: {
            message: 'The Region is required'
          }
        }
      },
      school: {
        validators: {
          notEmpty: {
            message: 'The School Name is required'
          }
        }
      },
      adress: {
        validators: {
          notEmpty: {
            message: 'The School Adress is required'
          }
        }
      },
      level: {
        validators: {
          integer: {
            message: 'The value is not an integer'
          }
        }
      }
    }

  }).on('success.form.bv', function(e) {

    $('#enrollment-form').data('bootstrapValidator').resetForm();

    e.preventDefault();

    var $form = $(e.target);
    var bv = $form.data('bootstrapValidator');

    $.ajax({
     url: $form.attr('action'),
     type: 'POST',
     data: $form.serialize()+"&"+getCookie("name")+"&"+getCookie("age"),
     success: function (data) {
      window.open('thankyou.html', '_self');
     },
     error: function (data) {
       if(debug){window.open('thankyou.html', '_self')}
      }
    });

  });
}

function preValidation(){

  $('#pre-register-form').bootstrapValidator({
    fields: {
      name: {
        validators: {
          notEmpty: {
            message: 'The Full Name is required'
          },
          regexp: {
            regexp: /^[a-zA-Z ]+$/,
            message: 'Your Full Name cannot have numbers or symbols'
          }
        }
      },
      age: {
        validators: {
          integer: {
            message: 'The value is not an integer'
          }
        }
      }
    }

  }).on('success.form.bv', function(e) {

    $('#pre-register-form').data('bootstrapValidator').resetForm();

    e.preventDefault();

    setParamsAndGo('enrollment.html', ['age', 'name'])

  });
}


function profileValidation(){

  $('#profile-form').bootstrapValidator({
    fields: {
      email: {
        validators: {
          notEmpty: {
            message: 'Please supply your email address'
          },
          emailAddress: {
            message: 'Please supply a valid email address'
          }
        }
      },
      name: {
        validators: {
          notEmpty: {
            message: 'The Full Name is required'
          },
          regexp: {
            regexp: /^[a-zA-Z ]+$/,
            message: 'Your Full Name cannot have numbers or symbols'
          }
        }
      },
      kids: {
        validators: {
          integer: {
            message: 'The value is not an integer'
          }
        }
      }
    }

  }).on('success.form.bv', function(e) {

    $('#profile-form').data('bootstrapValidator').resetForm();

    e.preventDefault();

    var $form = $(e.target);
    var bv = $form.data('bootstrapValidator');

    $.ajax({
     url: $form.attr('action'),
     type: 'POST',
     data: $form.serialize(),
     success: function (data) {

     },
     error: function (data) {

      }
    });

  });
}

function signInValidation(){

  $('#signin-form').bootstrapValidator({
    fields: {
      email: {
        validators: {
          notEmpty: {
            message: 'Please supply your email address'
          },
          emailAddress: {
            message: 'Please supply a valid email address'
          }
        }
      }
    }

  }).on('success.form.bv', function(e) {

    $('#signin-form').data('bootstrapValidator').resetForm();

    e.preventDefault();

    var $form = $(e.target);
    var bv = $form.data('bootstrapValidator');

    $.ajax({
     url: $form.attr('action'),
     type: 'POST',
     data: $form.serialize(),
     success: function (data) {
       setCookie('token', data);
       signin(data);
     },
     error: function (data) {
      console.log("Signin - Error: "+data);
      }
    });

    signin(token); // FAKE SIGN IN - REMOVE

  });
}

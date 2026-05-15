var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return typeof sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    return false;
};
$(document).ready(function () {
    $('#authorization_form button').on('click', function (event) {
        event.preventDefault();
        //console.log($(this).find('input[name=email]'));
        var email = $('#authorization_form').find('input[name=email]').val();
        var password = $('#authorization_form').find('input[name=password]').val();
        //console.log(email, password);
        $.ajax({
            type: 'POST',
            url: '/login',
            data: {email: email, password: password},
            success: function (data) {
                location.reload();
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
    });

    $('#registration').on('submit', function (event) {
        event.preventDefault();
        var email = $(this).find('input[name=email]').val();
        var password = $(this).find('input[name=password]').val();
        var firstName = $(this).find('input[name=firstName]').val();
        var lastName = $(this).find('input[name=lastName]').val();
        $.ajax({
            type: 'POST',
            url: '/register',
            data: {email: email, password: password, firstName: firstName, lastName: lastName},
            success: function (data) {
                location.reload();
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
    });

    $('body').on('change', '.new_select', function (event) {
        event.preventDefault();
        var lang = $(this).val();
        console.log(lang);
        $.ajax({
            type: 'POST',
            url: '/localization',
            data: {lang: lang},
            success: function (data) {
                location.reload();
            },
            error: function (error) {
                console.log(error);
            }
        });
    });

    $('#calculation_form').on('submit', function (event) {
        event.preventDefault();
        var cityFromId = $(this).find('select[name=city_from]').val();
        var cityToId = $(this).find('select[name=city_to]').val();
        var weight = $(this).find('input[name=weight]').val();
        var volume = $(this).find('input[name=volume]').val();
        $.ajax({
            type: 'POST',
            url: '/calculate-distance',
            data: {
                cityFromId: cityFromId,
                cityToId: cityToId,
                weight: weight,
                volume: volume
            },
            success: function (data) {
                $('.result_block').html("");
                $('.make_order').removeClass('disabled_order');
                $('.result_calculating_block').show().html(data);

                var cityChosen = $("select[name=city_to]").find('option:selected').text();
                console.log("cityChosen", cityChosen);
                $('.order_city_to_item span').html(cityChosen);
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
        return false;
    });


    $('body').on('click', '.catalog-list .item', function () {
        var idCity = $(this).attr('data-id');
        $('.city_choice').attr('data-chosen-city', idCity);
        $('.city_choice').fadeIn();

    });

    $('body').on('click', '.close_btn', function () {
        $(this).closest('.city_choice').fadeOut();
        $('.city_choice').attr('data-chosen-city', '');
    });

    $('body').on('click', '.city_from', function () {
        var cityId = $(this).closest('.city_choice').attr('data-chosen-city');
        $('select[name=city_from] option[value=' + cityId + ']').prop('selected', 'selected');
        $(this).closest('.city_choice').fadeOut();
        $([document.documentElement, document.body]).animate({
            scrollTop: $("select[name=city_from]").offset().top
        }, 1000);
    });

    $('body').on('click', '.city_to', function () {
        var cityId = $(this).closest('.city_choice').attr('data-chosen-city');
        $('select[name=city_to] option[value=' + cityId + ']').prop('selected', 'selected');
        $(this).closest('.city_choice').fadeOut();
        $('select[name=city_to]').trigger('change');
        $([document.documentElement, document.body]).animate({
            scrollTop: $("select[name=city_to]").offset().top
        }, 1000);
    });

    var filterParam = getUrlParameter("is_foreign");
    if (filterParam) {
        $('select[name=is_foreign] option[value=' + filterParam + ']').prop('selected', 'selected');
    }

    $('body').on('change', 'select[name=city_to]', function () {
        var cityChosen = $(this).find('option:selected').text();
        console.log("cityChosen", cityChosen);
        $('.order_city_to_item span').html(cityChosen);
    });


    $('body').on('change', '#calculation_form select, #calculation_form input', function () {
        if (!$('.make_order').hasClass('disabled_order')) {
            $('.make_order').addClass('disabled_order');
        }
    });

    $('body').on('click', '.make_order', function () {
        var cityFromId = $(this).closest('.calculating-block').find('select[name=city_from]').val();
        var cityToId = $(this).closest('.calculating-block').find('select[name=city_to]').val();
        var weight = $(this).closest('.calculating-block').find('input[name=weight]').val();
        var volume = $(this).closest('.calculating-block').find('input[name=volume]').val();
        var typeOfCargo = $(this).closest('.calculating-block').find('select[name=type_cargo]').val();
        var addressDelivery = $(this).closest('.calculating-block').find('input[name=address_to_delivery]').val();

        $.ajax({
            type: 'POST',
            url: '/make-order',
            data: {
                cityFromId: cityFromId,
                cityToId: cityToId,
                weight: weight,
                volume: volume,
                typeOfCargo: typeOfCargo,
                addressDelivery: addressDelivery
            },
            success: function (data) {
                console.log('$(\'.order_result\')', $('.order_result'));
                alert($('.order_result').text());
                window.location.href = "/profile-page";
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
        return false;
    });

    $('body').on('click', '.btn-create-receipt-payment', function () {
        var orderId = $(this).closest('.basket-row').attr('data-order-id');
        console.log('orderId', orderId);
        $.ajax({
            type: 'POST',
            url: '/create-receipt-payment',
            data: {
                orderId: orderId
            },
            success: function (data) {
                var text = $('.alert_result').text();
                alert(text);
                location.reload();
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
        return false;
    });

    $('body').on('click', '.del', function () {
        var orderId = $(this).closest('.basket-row').attr('data-order-id');
        $.ajax({
            type: 'POST',
            url: '/change-order-status',
            data: {
                orderId: orderId,
                orderStatus: 'cancel'
            },
            success: function (data) {
                var text = $('.alert_result').text();
                alert(text);
                location.reload();
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
        return false;
    });

    $('body').on('click', '.pay-btn', function () {
        var orderId = $(this).closest('.basket-row').attr('data-order-id');
        $.ajax({
            type: 'POST',
            url: '/change-order-status',
            data: {
                orderId: orderId,
                orderStatus: 'pay'
            },
            success: function (data) {
                var text = $('.alert_result').text();
                alert(text);
                location.reload();
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
        return false;
    });

    var sort = $('.sorting input[name=orderBy]').val();
    var sortType = $('.sorting input[name=typeSort]').val();

    $('body').on('click', '.basket-row.head .basket-cell[data-sort]', function () {
        var sortingBy = $(this).attr('data-sort');
        $('.sorting input[name=orderBy]').val(sortingBy);
        if ($('.sorting input[name=typeSort]').val() == 'ASC' && $('.sorting input[name=orderBy]').val() == sort) {
            $('.sorting input[name=typeSort]').val("DESC");
        } else {
            $('.sorting input[name=typeSort]').val("ASC");
        }
        $('.sorting').submit();
    });

    if (sort) {
        $('.basket-row.head .basket-cell[data-sort=' + sort + ']').addClass('sorted_el');
        if (sortType && sortType == 'DESC') {
            $('.basket-row.head .basket-cell[data-sort=' + sort + ']').addClass('desc_sort');
        }
    }


    $('body').on('click', '.dev-pagination a', function () {
        var paginationItem = $(this).attr('data-step');
        $('input[name=pagination_item]').val(paginationItem);
        $('.sorting').submit();
    });

    $('body').on('change', 'input[name=pagination_step]', function () {
        $('.sorting').submit();
    });

    $('body').on('click', '.make_report', function () {
        var typeReport = $(this).attr('data-type-report');
        $.ajax({
            type: 'POST',
            url: '/make-report',
            data: {
                typeReport: typeReport
            },
            success: function (data) {
                console.log(data);
                alert("success");
                // location.reload();
                window.location.href = data;
            },
            error: function (error) {
                console.log(error);
                $('.result_block').html(error.responseText);
            }
        });
        return false;
    });
});



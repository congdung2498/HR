$(document).ready(function () {
    if(Cookies.get('Cookie')==null){
        window.location.href = '../login.html';
    }

    $(document).ajaxError(function( event, jqxhr, settings, thrownError ) {
        console.log(jqxhr);
         if ( jqxhr.responseText == "Access Denied!" && jqxhr.status == 403 ) {
                    $.toaster({message: 'Bạn không có quyền thực hiện thao tác này ', title: ' Lỗi(403) Access Denied !!', priority: 'danger'});
                }
    });
    $(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    }); $("#news1").empty();

    var maNV = Cookies.get('UserName');
    $("#name").val(Cookies.get('HoVaTen'));

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/getNotice",
        dataType: 'json',
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#news1").append("<div class=\"grid col-md-6 col-lg-4 mb-4 mb-lg-4\" style=\" padding: 0;padding-left: 7px;padding-right: 7px; margin:0\">"+
                    " <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 99%;height: 230px;\" >"+
                    " <img src=\"../rest/getImg/"+data[i].img+"\"  alt=\"img12\"/>"+
                    " <figcaption>"+
                    "<div>" +
                    " <h2>News</h2>"+
                    "     <p>"+data[i].title+"</p>"+
                    "  </div>"+
                    " <a href=\"Detail.html\" onclick='detail("+data[i].iD+")'>View more</a>"+
                    "</figcaption>"+
                    "  </figure>"+
                    " <h2 class=\"font-size-regular\"  style=\"font-size: 20px;\"><a href=\"Detail.html\" onclick='detail("+data[i].iD+")'style=\"color: #0d0cb5;\">"+data[i].title+"</a></h2>"+
                    " <div class=\"meta mb-1\"  style=\"color: #696666;\">"+data[i].author+"<span class=\"mx-2\">&bullet;</span>"+data[i].date+"</div>"+
                    " <p id=\"summary\">"+data[i].summary+"</p>"+
                    "   <p><a style=\"color: #0d0cb5;\" href=\"Detail.html\" onclick='detail("+data[i].iD+")'>Continue Reading...</a></p>"+
                    "</div>")
            }
        }
    });
    var Listdata=[];
    var Listdate=[];
    var url = "/rest/getAllMonthByMaNV?maNV=" + maNV;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        url: url,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            Listdata = data;
            for(var i=0; i< Listdata.length; i++){
                var d= stringToDate(Listdata[i],"MM/yyyy","/");
                Listdate.push(d);
            }
            Listdate.sort(date_sort_desc);

            var ListYear = [];

            for(var i=0; i< Listdate.length; i++){
                var d = Listdate[i].getFullYear();
                ListYear.push(d);
            }
            var yearunique = ListYear.filter( onlyUnique );
            $("#year").empty();
            for (var i = 0; i < yearunique.length; i++) {
                $("#year").append(new Option(yearunique[i], yearunique[i]));
            }
            $("#year").change();
        }
    });



    $(".to").change(function () {
        var from = $('.from').val();
        var to = $('.to').val();
        var url = "/rest/getSalarybyYear?maNV=" + maNV+"&from="+from+"&to="+to;
        $.ajax({
            type: "GET",
            contentType: "application/json",
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            url: url,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#barChart").empty();
                var thang=[];
                var tongthunhap=[];
                var sotiennhan = [];
                for (var i = 0; i < data.length; i++) {
                    thang.push(data[i].thang)
                    tongthunhap.push(data[i].tongThuNhap);
                    sotiennhan.push(data[i].soTienChuyenKhoan);
                }

                myChart.data.labels=thang;
                myChart.data.datasets[0].data=tongthunhap;
                myChart.data.datasets[1].data=sotiennhan;
                myChart.update();
            },
            error: function (data) {
                $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
            }

        });
    });
    var d = new Date();
    var n = d.getFullYear();
    $('.from').val("01/"+n+"");
    $('.to').val("12/"+n+"");
    $(".to").change();
    function onlyUnique(value, index, self) {
        return self.indexOf(value) === index;
    }
    var date_sort_desc = function (date1, date2) {
        // This is a comparison function that will result in dates being sorted in
        // DESCENDING order.
        if (date1 > date2) return -1;
        if (date1 < date2) return 1;
        return 0;
    };
    function stringToDate(_date,_format,_delimiter)
    {
        var formatLowerCase=_format.toLowerCase();
        var formatItems=formatLowerCase.split(_delimiter);
        var dateItems=_date.split(_delimiter);
        var monthIndex=formatItems.indexOf("mm");
        var yearIndex=formatItems.indexOf("yyyy");
        var month=parseInt(dateItems[monthIndex]);
        month-=1;
        var formatedDate = new Date(dateItems[yearIndex],month);
        return formatedDate;
    }
    //bar chart
    var ctx = document.getElementById( "barChart" );
    //    ctx.height = 200;
    var myChart = new Chart( ctx, {
        type: 'bar',
        data: {
            labels: [ "January", "February", "March", "April", "May", "June", "July","August", "September", "October", "November", "December"],
            datasets: [
                {
                    label: "Tổng thu nhập",
                    data: [ ],
                    borderColor: "rgba(0, 123, 255, 0.9)",
                    borderWidth: "0",
                    backgroundColor: "rgba(0,0,0,0.07)"
                },
                {
                    label: "Số tiền nhận",
                    data: [  ],
                    borderColor: "rgba(0,0,0,0.09)",
                    borderWidth: "0",
                    backgroundColor: "rgba(0, 123, 255, 0.9)"
                }
            ]
        },
        options: {
            scales: {
                yAxes: [ {
                    ticks: {
                        beginAtZero: true
                    }
                } ]
            },
            tooltips: {
                callbacks: {
                    label: function(tooltipItem, data) {
                        return formatNumber(tooltipItem.yLabel.toString()); }, },
            },
            scales: {
                yAxes: [{
                    ticks: {
                        callback: function(label, index, labels) { return formatNumber(label); },
                        beginAtZero:true,
                        fontSize: 10,
                    },
                    gridLines: {
                        display: true
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'Số tiền (đồng)',
                        fontSize: 10,
                    }
                }],
                xAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontSize: 10
                    },
                    gridLines: {
                        display:true
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'Tháng',
                        fontSize: 10,
                    }
                }]
            }
        },
    } );
    function formatNumber(num) {
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
    }
    var startDate = new Date();
    var fechaFin = new Date();
    var FromEndDate = new Date();
    var ToEndDate = new Date();

    $('.from').datepicker({
        autoclose: true,
        minViewMode: 1,
        format: 'mm/yyyy'
    }).on('changeDate', function(selected){
        startDate = new Date(selected.date.valueOf());
        startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
        $('.to').datepicker('setStartDate', startDate);
    });

    $('.to').datepicker({
        autoclose: true,
        minViewMode: 1,
        format: 'mm/yyyy'
    }).on('changeDate', function(selected){
        FromEndDate = new Date(selected.date.valueOf());
        FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date.valueOf())));
        $('.from').datepicker('setEndDate', FromEndDate);
    });


    var a= 0;
    !function() {

        var today = moment();

        function Calendar(selector, events) {
            this.el = document.querySelector(selector);
            this.events = events;
            this.current = moment().date(1);
            if(a==0) {
                this.drawHeader();
            }


            //Draw Month
            this.drawMonth();

            this.drawLegend();


            var current = document.querySelector('.today');
            if(current) {
                // var self = this;
                // window.setTimeout(function() {
                //     self.openDay(current);
                // }, 500);
            }
        }

        Calendar.prototype.draw = function() {
            a=1;
            this.drawHeader();
            //
            // // //Draw Month
            this.drawMonth();
            //
            //
            this.drawLegend();

            var thang = $("#calendar").find("h1").text();
            var d = new Date(thang);
            getTimeSheet(d);
        }

        Calendar.prototype.drawHeader = function() {
            var self = this;
            if(!this.header) {
                //Create the header elements
                this.header = createElement('div', 'header');
                this.header.className = 'header';

                this.title = createElement('h1');

                var right = createElement('div', 'right');
                right.addEventListener('click', function() { self.nextMonth(); });

                var left = createElement('div', 'left');
                left.addEventListener('click', function() { self.prevMonth(); });

                //Append the Elements
                this.header.appendChild(this.title);
                this.header.appendChild(right);
                this.header.appendChild(left);
                this.el.appendChild(this.header);
            }

            this.title.innerHTML = this.current.format('MMMM YYYY');
        }

        Calendar.prototype.drawMonth = function() {
            var self = this;

            this.events.forEach(function(ev) {

                console.log(ev);
                if(ev.date._d == null){
                    ev.date = self.current.clone().date(ev.date.getDate());
                }

            });
            console.log(this.month);
            if(this.month) {
                this.oldMonth = this.month;
                this.oldMonth.className = 'month out ' + (self.next ? 'next' : 'prev');
                this.oldMonth.addEventListener('webkitAnimationEnd', function() {
                    self.oldMonth.parentNode.removeChild(self.oldMonth);
                    self.month = createElement('div', 'month');
                    self.backFill();
                    self.currentMonth();
                    self.fowardFill();
                    self.el.appendChild(self.month);
                    window.setTimeout(function() {
                        self.month.className = 'month in ' + (self.next ? 'next' : 'prev');
                    }, 16);
                });
            } else {
                this.month = createElement('div', 'month');
                this.el.appendChild(this.month);
                this.backFill();
                this.currentMonth();
                this.fowardFill();
                this.month.className = 'month new';
            }



        }

        Calendar.prototype.backFill = function() {
            var clone = this.current.clone();
            var dayOfWeek = clone.day();

            if(!dayOfWeek) { return; }

            clone.subtract('days', dayOfWeek+1);

            for(var i = dayOfWeek; i > 0 ; i--) {
                this.drawDay(clone.add('days', 1));
            }
        }

        Calendar.prototype.fowardFill = function() {
            var clone = this.current.clone().add('months', 1).subtract('days', 1);
            var dayOfWeek = clone.day();

            if(dayOfWeek === 6) { return; }

            for(var i = dayOfWeek; i < 6 ; i++) {
                this.drawDay(clone.add('days', 1));
            }
        }

        Calendar.prototype.currentMonth = function() {
            var clone = this.current.clone();

            while(clone.month() === this.current.month()) {
                this.drawDay(clone);
                clone.add('days', 1);
            }
        }

        Calendar.prototype.getWeek = function(day) {
            if(!this.week || day.day() === 0) {
                this.week = createElement('div', 'week');
                this.month.appendChild(this.week);
            }
        }

        Calendar.prototype.drawDay = function(day) {
            var self = this;
            this.getWeek(day);

            //Outer Day
            var outer = createElement('div', this.getDayClass(day));
            outer.addEventListener('click', function() {
                self.openDay(this);
            });

            //Day Name
            var name = createElement('div', 'day-name', day.format('ddd'));

            //Day Number
            var number = createElement('div', 'day-number', day.format('DD'));


            //Events
            var events = createElement('div', 'day-events');
            this.drawEvents(day, events);

            outer.appendChild(name);
            outer.appendChild(number);
            outer.appendChild(events);
            this.week.appendChild(outer);
        }

        Calendar.prototype.drawEvents = function(day, element) {
            if(day.month() === this.current.month()) {
                var todaysEvents = this.events.reduce(function(memo, ev) {
                    if(ev.date.isSame(day, 'day')) {
                        memo.push(ev);
                    }
                    return memo;
                }, []);
                todaysEvents.forEach(function(ev) {
                    var evSpan = createElement('i', ev.color);
                    element.appendChild(evSpan);
                });
            }
        }

        Calendar.prototype.getDayClass = function(day) {
            classes = ['day'];
            if(day.month() !== this.current.month()) {
                classes.push('other');
            } else if (today.isSame(day, 'day')) {
                classes.push('today');
            }
            return classes.join(' ');
        }

        Calendar.prototype.openDay = function(el) {
            var details, arrow;
            var dayNumber = +el.querySelectorAll('.day-number')[0].innerText || +el.querySelectorAll('.day-number')[0].textContent;
            var day = this.current.clone().date(dayNumber);

            var currentOpened = document.querySelector('.details');

            //Check to see if there is an open detais box on the current row
            if(currentOpened && currentOpened.parentNode === el.parentNode) {
                details = currentOpened;
                arrow = document.querySelector('.arrow');
            } else {
                //Close the open events on differnt week row
                //currentOpened && currentOpened.parentNode.removeChild(currentOpened);
                if(currentOpened) {
                    currentOpened.addEventListener('webkitAnimationEnd', function() {
                        currentOpened.parentNode.removeChild(currentOpened);
                    });
                    currentOpened.addEventListener('oanimationend', function() {
                        currentOpened.parentNode.removeChild(currentOpened);
                    });
                    currentOpened.addEventListener('msAnimationEnd', function() {
                        currentOpened.parentNode.removeChild(currentOpened);
                    });
                    currentOpened.addEventListener('animationend', function() {
                        currentOpened.parentNode.removeChild(currentOpened);
                    });
                    currentOpened.className = 'details out';
                }

                //Create the Details Container
                details = createElement('div', 'details in');

                //Create the arrow
                var arrow = createElement('div', 'arrow');

                //Create the event wrapper

                details.appendChild(arrow);
                el.parentNode.appendChild(details);
            }

            var todaysEvents = this.events.reduce(function(memo, ev) {
                if(ev.date.isSame(day, 'day')) {
                    memo.push(ev);
                }
                return memo;
            }, []);

            this.renderEvents(todaysEvents, details);

            arrow.style.left = el.offsetLeft - el.parentNode.offsetLeft + 27 + 'px';
        }

        Calendar.prototype.renderEvents = function(events, ele) {
            //Remove any events in the current details element
            var currentWrapper = ele.querySelector('.events');
            var wrapper = createElement('div', 'events in' + (currentWrapper ? ' new' : ''));

            events.forEach(function(ev) {
                var div = createElement('div', 'event');
                var square = createElement('div', 'event-category ' + ev.color);
                var span = createElement('span', '', ev.eventName);

                div.appendChild(square);
                div.appendChild(span);
                wrapper.appendChild(div);
            });

            if(!events.length) {
                var div = createElement('div', 'event empty');
                var span = createElement('span', '', 'No Events');

                div.appendChild(span);
                wrapper.appendChild(div);
            }

            if(currentWrapper) {
                currentWrapper.className = 'events out';
                currentWrapper.addEventListener('webkitAnimationEnd', function() {
                    currentWrapper.parentNode.removeChild(currentWrapper);
                    ele.appendChild(wrapper);
                });
                currentWrapper.addEventListener('oanimationend', function() {
                    currentWrapper.parentNode.removeChild(currentWrapper);
                    ele.appendChild(wrapper);
                });
                currentWrapper.addEventListener('msAnimationEnd', function() {
                    currentWrapper.parentNode.removeChild(currentWrapper);
                    ele.appendChild(wrapper);
                });
                currentWrapper.addEventListener('animationend', function() {
                    currentWrapper.parentNode.removeChild(currentWrapper);
                    ele.appendChild(wrapper);
                });
            } else {
                ele.appendChild(wrapper);
            }
        }

        Calendar.prototype.drawLegend = function() {
            var legend = createElement('div', 'legend');
            var calendars = this.events.map(function(e) {
                return e.calendar + '|' + e.color;
            }).reduce(function(memo, e) {
                if(memo.indexOf(e) === -1) {
                    memo.push(e);
                }
                return memo;
            }, []).forEach(function(e) {
                var parts = e.split('|');
                var entry = createElement('span', 'entry ' +  parts[1], parts[0]);
                legend.appendChild(entry);
            });
            this.el.appendChild(legend);
        }

        Calendar.prototype.nextMonth = function() {
            this.current.add('months', 1);
            this.next = true;
            this.draw();
        }

        Calendar.prototype.prevMonth = function() {
            this.current.subtract('months', 1);
            this.next = false;
            this.draw();
        }

        window.Calendar = Calendar;

        function createElement(tagName, className, innerText) {
            var ele = document.createElement(tagName);
            if(className) {
                ele.className = className;
            }
            if(innerText) {
                ele.innderText = ele.textContent = innerText;
            }
            return ele;
        }
    }();
    var total = 0;
    !function() {

        data = [];
        var timesheet = {};
        var now = new Date();

        var month = now.getMonth()+1;
        var year = now.getFullYear();

        getTimeSheet(now);

        function addDate(ev) {

        }



    }();
    function getTimeSheet(date) {
        total = 0;
        $("#total").empty();
        $(".month").empty();
        $(".legend").empty();
        data=[];

        var timesheet = {};

        var month = date.getMonth()+1;
        var year = date.getFullYear();

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/rest/getTimeSheetbyStaffMonth",
            dataType: 'json',
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            cache: false,
            data: {
                "maNV": maNV,
                "month": month,
                "year": year
            },
            timeout: 600000,
            success: function (dt) {
                for(var i = 0 ; i< dt.length; i++){
                    timesheet={};
                    var string ="";
                    var startH;
                    var startM;

                    var endH;
                    var endM;
                    console.log(dt);
                    for(var j=0; j<dt[i].listTime.length; j++){
                        console.log(dt[i].listTime[j]);
                        if(dt[i].listTime[j].starttime!= null){
                            startH = new Date(dt[i].listTime[j].starttime).getHours();
                            startM = new Date(dt[i].listTime[j].starttime).getMinutes()
                        }
                        else {
                            startH = "";
                            startM = "";
                        }
                        if(dt[i].listTime[j].endtime!= null){
                            endH = new Date(dt[i].listTime[j].endtime).getHours();
                            endM = new Date(dt[i].listTime[j].endtime).getMinutes()
                        }
                        else{
                            endH = "";
                            endM = "";
                        }
                        string += " Giờ vào: "+startH+":"+startM+"  -  Giờ ra:  "+endH+":"+endM+"   "

                    }
                    timesheet.eventName= string;
                    timesheet.date = new Date(dt[i].date);
                    if(dt[i].work == 0.5)  {
                        total += 0.5;
                        timesheet.calendar="Công 0.5";
                        timesheet.color="fa fa-minus";
                    }
                    else if(dt[i].work == 1) {
                        total += 1;
                        timesheet.calendar = "Công 1";
                        timesheet.color="fa fa-check";
                    }else{
                        timesheet.calendar = "";
                    }
                    data.push(timesheet);
                }
                console.log(data);
                var calendar = new Calendar('#calendar', data);
                $("#total").html("Tổng công: <b style='font-weight: bold'>"+total+"</b>")

            }
        });




        function addDate(ev) {

        }



    }

});
function detail(id) {
    localStorage.setItem("id",id);
}

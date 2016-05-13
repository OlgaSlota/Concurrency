var async = require('async');

function printAsync(s, cb) {
   var delay = Math.floor((Math.random()*1000)+500);
   setTimeout(function() {
       console.log(s);
       if (cb) cb();
   }, delay);
}

async.waterfall(
    [       
function task1(cb) {
    printAsync("1", cb);
},
       
function task2(cb) {
    printAsync("2", cb);
},
       
function task3(cb) {
    printAsync("3", cb);
},
function last(cb){
	console.log("done!");
}  
  ]
);
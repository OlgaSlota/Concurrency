var async = require("async");

var Fork = function () {
    this.state = 0;
    this.max = 5; // max tries number
    this.maxDelay = 500;
    return this;
};

Fork.prototype.acquire = function (cbs, cbf) {
    var count = 0;
    var time = 1;
    var takeFork = function (waitTime, fork) {
        setTimeout(function () {
            if (fork.state == 1) {
                if (time < fork.maxDelay) time *= 2;
                if (count > fork.max) {
                    if (cbf) cbf();
                }
                else {
                    count++;
                    takeFork(Math.floor(Math.random() * time), fork);
                }
            } else {
                fork.state = 1;
                if (cbs) cbs();
            }
        }, waitTime);
    };
    takeFork(1, this);
};

Fork.prototype.release = function (cb) {
    this.state = 0;
    if (cb) cb();
};

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    this.eatTime = 10;
    return this;
};

Philosopher.prototype.eat = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    setTimeout(function () {
        async.waterfall([
            function (cb) {
                forks[f1].release(cb);
            },
            function (cb) {
                forks[f2].release(cb);
            },
            function (cb) {
                philosophers[id].startNaive(count - 1);
            }
        ]);
    }, this.eatTime);
};

Philosopher.prototype.startNaive = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    if (startTimesNaive[id] === undefined) 
		startTimesNaive[id] = new Date().getTime();

    if (count != 0) {
        forks[f1].acquire(function () {
            forks[f2].acquire(function () {
                philosophers[id].eat(count);
            }, function () {
                async.waterfall([
                    function (cb) {
                        forks[f1].release(cb);
                    },
                    function (cb) {
                        philosophers[id].startNaive(count);
                    }
                ]);
            })
        }, function () {
            philosophers[id].startNaive(count);
        });
    } else {
        console.log(id, 'time:', new Date().getTime() - startTimesNaive[id]);
    }
};

Philosopher.prototype.startAsym = function (count) {
    if(this.id%2==0) { // swap forks for even
        var tmp = this.f1;
        this.f1 = this.f2;
        this.f2 = tmp;
    }
    this.startNaive(count);
};

Philosopher.prototype.giveForks = function (count) {
    var philosopher = this;
    var r = Math.floor(Math.random() * this.eatTime);
    startTimesConductor[philosopher.id] += r;
    setTimeout(function () {
        conductor.release(philosopher, count);
    }, r);
};

Philosopher.prototype.startConductor = function (count) {
    var id = this.id;
    if (startTimesConductor[id] === undefined) startTimesConductor[id] = new Date().getTime();
    if (count != 0) conductor.ask(this, count);
    else console.log( id, 'time:' ,new Date().getTime() - startTimesConductor[id]);
};

var Conductor = function () {
    this.queue = [];
    return this;
};

Conductor.prototype.ask = function (philosopher, count) {
    var id = philosopher.id,
        f1 = philosopher.f1,
        f2 = philosopher.f2,
        forks = philosopher.forks;
    if (forks[f1].state === 0 && forks[f2].state === 0) {
        forks[f1].state = 1;
        forks[f2].state = 1;
        philosopher.giveForks(count);
    } else {
        this.queue.push([id, count]);
    }
};

Conductor.prototype.release = function (philosopher, count) {
    var f1 = philosopher.f1,
        f2 = philosopher.f2,
        forks = philosopher.forks,
        conductor = this;
    forks[f1].state = 0;
    forks[f2].state = 0;
    philosopher.startConductor(count - 1);
    var disposeQueue = function () {
        if (conductor.queue.length !== 0) {
            var id = conductor.queue[0][0],
                count = conductor.queue[0][1],
                f1 = philosophers[id].f1,
                f2 = philosophers[id].f2,
                forks = philosophers[id].forks;
            if (forks[f1].state === 0 && forks[f2].state === 0) {
                conductor.queue.shift();
                forks[f1].state = 1;
                forks[f2].state = 1;
                philosophers[id].giveForks(count);
                disposeQueue();
            }
        }
    };
    disposeQueue();
};

var N = 5;
var forks = [];
var philosophers = [];
var startTimesNaive = [];
var startTimesAsym = [];
var startTimesConductor = [];
var conductor = new Conductor();

for (var i = 0; i < N; i++) forks.push(new Fork());
for (var i = 0; i < N; i++) philosophers.push(new Philosopher(i, forks));
for (var i = 0; i < N; i++) philosophers[i].startNaive(15);

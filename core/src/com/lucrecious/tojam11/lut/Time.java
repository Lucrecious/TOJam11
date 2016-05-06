package com.lucrecious.tojam11.lut;

import com.nilunder.bdx.Bdx;

public class Time {
    public static class StopWatch {
        private float startTimeSec;
        private float currentDelta;
        private boolean _timing;

        public StopWatch() {
            currentDelta = 0;
            _timing = false;
        }

        public float secondsPassed() {
            if (_timing) {
                return currentDelta + Bdx.time - startTimeSec;
            } else {
                return currentDelta;
            }
        }

        public void start() {
            if (!_timing) {
                _timing = true;
                startTimeSec = Bdx.time;
            }
        }

        public void stop() {
            if (_timing) {
                _timing = false;
                currentDelta += Bdx.time - startTimeSec;
            }
        }

        public void reset() {
            _timing = false;
            currentDelta = 0;
        }

        public boolean timing() {
            return _timing;
        }
    }

    public static class Timer extends StopWatch {
        private float startTimeSec;

        public Timer(float startTimeSec) {
            this.startTimeSec = startTimeSec;
        }

        public boolean finished() {
            if (startTimeSec - secondsPassed() <= 0) {
                return true;
            }

            return false;
        }

        public float secondsLeft() {
            return finished() ? 0 : startTimeSec - secondsPassed();
        }
    }

}
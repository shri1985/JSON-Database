import java.util.ArrayList;


public class ObserverList {
    private class KeyObserver {
        private String key;
        private Observer observer;
        public KeyObserver(String key, Observer observer) {
            this.key = key;
            this.observer = observer;
        }
        public String getKey() {return key;}
        public Observer getObserver() {return observer;}
    }
    private ArrayList<KeyObserver> observerArray;

    public ObserverList() {
        observerArray = new ArrayList<KeyObserver>();
    }

    public void addObserver(String key, Observer observer) {

        observerArray.add(new KeyObserver(key, observer));
    }

    public void removeObserver(String key, Observer observer) {

        int index = -1;
        for (KeyObserver keyObserver : observerArray) {
           if (keyObserver.getKey().equals(key) &&
                   keyObserver.getObserver().equals(observer)) {
               index = observerArray.indexOf(keyObserver);
               break;
           }
        }

        if (index > -1) {
            observerArray.remove(index);
        }
    }

    public ArrayList<Observer> getObservers(String key) {

        ArrayList<Observer> observers = new ArrayList<Observer>();
        int index = -1;
        for (KeyObserver keyObserver : observerArray) {
            if (keyObserver.getKey().equals(key)) {

                observers.add(keyObserver.getObserver());
            }
        }
        if (observers.size() > 0) {
            return observers;
        } else {
            return null;
        }
    }

    public void checkForNotify(String key, Object value) {
        ArrayList<Observer> observers = getObservers(key);
        if (observers != null && observers.size() > 0) {

            for (Observer observer: observers) {
                observer.notfiy(value);
            }
        }
    }

}

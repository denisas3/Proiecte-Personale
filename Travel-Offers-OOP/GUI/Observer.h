//
// Observar si Observabil
//

#ifndef OBSERVER_H
#define OBSERVER_H
#include <vector>
#include <bits/algorithmfwd.h>

class Observer {
public:
	virtual void update()=0;
	virtual ~Observer()=default;
};

class Observable {
private:
	std::vector<Observer*> observers;
public:
	void add_observer(Observer* observer) {
		observers.push_back(observer);
	}
	void remove_observer(Observer* observer) {
		observers.erase(std::remove(observers.begin(), observers.end(), observer), observers.end());
	}
protected:
	void notify() {
		for (auto observer : observers) {
			observer->update();
		}
	}
};


#endif //OBSERVER_H

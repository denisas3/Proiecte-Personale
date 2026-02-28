//
// Created by Blondi on 3/31/2025.
//

#ifndef EXCEPTII_H
#define EXCEPTII_H
#include <string>
using std::string;


class Exceptii : public std::exception {
private:
    string mesaj;
public:
    explicit Exceptii(const string& mesaj);
    const char* what() const noexcept override;
};



#endif //EXCEPTII_H

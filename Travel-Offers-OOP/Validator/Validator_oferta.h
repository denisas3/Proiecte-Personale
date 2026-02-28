//
// Created by Blondi on 3/31/2025.
//

#ifndef VALIDATOR_OFERTA_H
#define VALIDATOR_OFERTA_H
#include <string>
using std::string;


class Validator_oferta {

    public:
        static void validare_oferta(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret);

};



#endif //VALIDATOR_OFERTA_H

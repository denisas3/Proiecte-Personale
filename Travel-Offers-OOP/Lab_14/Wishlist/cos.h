//
// Created by Blondi on 4/29/2025.
//

#ifndef COS_H
#define COS_H
#include <vector>
#include <string>
#include "../Domeniu/oferta.h"
using std::vector;

class Cos {
    private:
        std::vector<Oferta> cos;
    public:
        Cos() = default;
        ~Cos() = default;
        void goleste_cos();
        void adauga_in_cos(const Oferta &oferta);
        vector<Oferta> genereaza_cos(int nr_oferte, vector<Oferta> &oferte);
        const vector<Oferta> &get_cos() const;
        int size_cos() const;
        void exportCos(const std::string &numeFisier) const;
};

#endif //COS_H

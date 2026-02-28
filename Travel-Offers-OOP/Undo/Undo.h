//
// Created by Blondi on 5/6/2025.
//

#ifndef UNDO_H
#define UNDO_H
#include "../Domeniu/oferta.h"
#include "../Repo/Repo_oferta.h"

class Undo {
    public:
        virtual void doUndo() = 0;
        virtual ~Undo()= default;
};

class UndoAdauga :public Undo {
    Oferta oferta_adaugata;
    Repo_oferta& repo;
    public:
        UndoAdauga(Repo_oferta& repo, const Oferta& oferta) : repo {repo}, oferta_adaugata {oferta} {}
        void doUndo() override {
            repo.sterge(oferta_adaugata);
        }
};

class UndoSterge :public Undo {
    Oferta oferta_stearsa;
    Repo_oferta& repo;
public:
    UndoSterge(Repo_oferta& repo, const Oferta& oferta) : repo {repo}, oferta_stearsa {oferta} {}
    void doUndo() override {
        repo.adauga(oferta_stearsa);
    }
};

class UndoModifica :public Undo {
    Oferta oferta_nemodificata;
    Repo_oferta& repo;
public:
    UndoModifica(Repo_oferta& repo, const Oferta& oferta_nemodificata ) : repo (repo), oferta_nemodificata (oferta_nemodificata){}
    void doUndo() override {
        repo.modifica(oferta_nemodificata);
    }
};

#endif //UNDO_H

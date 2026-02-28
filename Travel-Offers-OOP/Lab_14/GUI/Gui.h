//
// Created by Blondi on 5/12/2025.
//

#ifndef GUI_H
#define GUI_H
#include "../Model_view/model.h"
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qmessagebox.h>
#include <QtWidgets/qwidget.h>
#include <vector>
#include <string>
#include <QtWidgets/QComboBox>
#include <QtWidgets/QCheckBox>
#include <QTableWidgetItem>

#include "Gui.h"
#include "Observer.h"
using std::vector;
using std::string;
#include "../Service/Service_oferta.h"
#include "../Domeniu/oferta.h"
#include "../Erori/Exceptii.h"
#include <QPainter>
#include <QRandomGenerator>
/// grafice vizuale
class Wishlist_read_gui: public QWidget, public Observer {
public:
    Wishlist_read_gui(Service_oferta& service): service{service} {
        service.add_observer(this);
    }
private:
    Service_oferta& service;
    void update() override {
        repaint();
    }
protected:
    void paintEvent(QPaintEvent *event) override{
        QPainter painter(this);
        auto lista_oferte = service.service_get_cos();
        for (const auto& oferta : lista_oferte) {
            int x = QRandomGenerator::global()->bounded(width());
            int y = QRandomGenerator::global()->bounded(height());
            int size = 30;
            painter.setBrush(QBrush(Qt::darkCyan));
            painter.drawPolygon(QPolygonF({QPointF(x, y), QPointF(x + size, y), QPointF(x + size, y + size), QPointF(x, y + size)}));
        }
    }
};


class Wishlist_gui: public QWidget, public Observer {
public:
    Wishlist_gui(Service_oferta& service): service{service} {
        service.add_observer(this);
        init_Wishlist_gui();
        loadOferte();
        initConnect();
    }

    void update() override {
        model->updateModel();
    }

private:
    Model* model;
    Service_oferta& service;
    QTableView* tabel = new QTableView();
    QListWidget* lista = new QListWidget();
    QPushButton* buton_exit = new QPushButton("&Exit");
    QPushButton* buton_adauga = new QPushButton("&Adauga in wishlist.");
    QPushButton* buton_goleste = new QPushButton("&Goleste din wishlist.");
    QPushButton* buton_genereaza = new QPushButton("&Genereaza wishlist.");
    QPushButton* buton_afisare = new QPushButton("&Afiseaza ofertele din wishlist.");
    QPushButton* buton_clear = new QPushButton("&Clear lista.");
    QFormLayout* form = new QFormLayout;
    QLineEdit* id_oferta = new QLineEdit();
    QLineEdit* denumire_oferta = new QLineEdit();
    QLineEdit* destinatie_oferta = new QLineEdit();
    QLineEdit* tip_oferta = new QLineEdit();
    QLineEdit* pret_oferta = new QLineEdit();

    void init_Wishlist_gui() {
        QHBoxLayout* main_layout = new QHBoxLayout();
        setLayout(main_layout);
        model = new Model(service);
        tabel->setModel(model);
        // tabel->setColumnCount(5);
        // tabel->setHorizontalHeaderLabels({"Id oferta","Denumire oferta", "Destinatie oferta", "Tip oferta", "Pret oferta"});
        //tabel->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
        main_layout->addWidget(tabel);
        auto miau = new QHBoxLayout;
        auto button_box = new QVBoxLayout;
        button_box->addWidget(buton_adauga);
        button_box->addWidget(buton_goleste);
        button_box->addWidget(buton_genereaza);
        button_box->addWidget(buton_afisare);
        button_box->addWidget(buton_clear);
        button_box->addWidget(buton_exit);

        form->addRow("Id oferta", id_oferta);
        miau->addLayout(form);
        miau->addLayout(button_box);
        main_layout->addLayout(miau);
    }

    void loadOferte() {
        // tabel->clear();
        // tabel->setRowCount(0);
        // vector<Oferta> lista_oferte = service.service_get_cos();
        // tabel->insertColumn(5);
        // for (const auto& oferta : lista_oferte) {
        //     tabel->setHorizontalHeaderLabels({"Id oferta","Denumire oferta", "Destinatie oferta", "Tip oferta", "Pret oferta"});
        //     int row = tabel->rowCount();
        //     tabel->insertRow(row);
        //     tabel->setItem(row, 0, new QTableWidgetItem(QString::number(oferta.getId())));
        //     tabel->setItem(row, 1, new QTableWidgetItem(QString::fromStdString(oferta.getDenumire())));
        //     tabel->setItem(row, 2, new QTableWidgetItem(QString::fromStdString(oferta.getDestinatie())));
        //     tabel->setItem(row, 3, new QTableWidgetItem(QString::fromStdString(oferta.getTip())));
        //     tabel->setItem(row, 4, new QTableWidgetItem(QString::number(oferta.getPret())));
        // }
        model->updateModel();
    }


    void initConnect() {
        QObject::connect(buton_exit, &QPushButton::clicked, [&]() {
            close();
        });

        QObject::connect(buton_clear, &QPushButton::clicked, [&]() {
            loadOferte();
        });

        QObject::connect(buton_adauga, &QPushButton::clicked, [&]() {
            auto id = id_oferta->text().toStdString();
            service.service_adauga_in_cos(stoi(id));
            loadOferte();
        });

        QObject::connect(buton_goleste, &QPushButton::clicked, [&]() {
            //tabel->clear();
            service.service_goleste_cos();
            loadOferte();
        });

        QObject::connect(buton_genereaza, &QPushButton::clicked, [&]() {
            try {
                auto oferte = service.service_genereaza_cos();
                //tabel->clear();
                //tabel->setRowCount(0);
                loadOferte();
            } catch (const std::exception& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        QObject::connect(buton_afisare, &QPushButton::clicked, [&]() {
            try {
                auto oferte = service.service_get_cos();
                if (oferte.empty()) {
                    QMessageBox::warning(nullptr, "Info", "Cosul este gol!");
                }
                //tabel->clear();
                loadOferte();
             } catch (const std::exception& e) {
                 QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
             }
        });
    }
};

class Sortare_gui: public QWidget {
public:
    Sortare_gui(Service_oferta& service): service{service} {
        init_Sortare_gui();
        loadOferte();
        initConnect();
    }
private:
    Service_oferta& service;
    QListWidget* lista = new QListWidget();
    QPushButton* buton_sortare = new QPushButton("&Sortare.");
    QPushButton* buton_exit = new QPushButton("&Exit");
    QCheckBox* denumire = new QCheckBox();
    QCheckBox* destinatie = new QCheckBox();
    QCheckBox* tip_pret = new QCheckBox();
    QCheckBox* cres = new QCheckBox();
    QCheckBox* descresc = new QCheckBox();

    void init_Sortare_gui() {
        QHBoxLayout* main_layout = new QHBoxLayout();
        setLayout(main_layout);
        main_layout->addWidget(lista);
        auto vbox = new QVBoxLayout;
        vbox->addWidget(denumire);
        vbox->addWidget(destinatie);
        vbox->addWidget(tip_pret);
        auto vbox1 = new QVBoxLayout;
        vbox1->addWidget(cres);
        vbox1->addWidget(descresc);
        auto button_box = new QVBoxLayout;
        button_box->addWidget(buton_sortare);
        button_box->addWidget(buton_exit);
        main_layout->addLayout(vbox);
        main_layout->addLayout(vbox1);
        main_layout->addLayout(button_box);

        denumire->setText("Denumire");
        destinatie->setText("Destinatie");
        tip_pret->setText("Tip&Pret");
        cres->setText("Sortare crescatoare");
        descresc->setText("Sortare descrescatoare");
    }

    void loadOferte() {
        lista->clear();
        vector<Oferta> lista_oferte = service.get_oferte();
        for (const auto& oferta : lista_oferte) {
            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
        }
    }

    void initConnect() {
        QObject::connect(buton_exit, &QPushButton::clicked, [&]() {
            close();
        });

        QObject::connect(buton_sortare, &QPushButton::clicked, [&]() {
            if (denumire->isChecked() && !destinatie->isChecked() && !tip_pret->isChecked()) {
                if (cres->isChecked()) {
                    try {
                        lista->clear();
                        vector<Oferta> lista_oferte = service.service_sortare_denumire(false);
                        for (const auto& oferta : lista_oferte) {
                            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                         }
                    }
                    catch (const Exceptii& e) {
                        QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
                    }
                }
                else {
                    try {
                        lista->clear();
                        vector<Oferta> lista_oferte = service.service_sortare_denumire(true);
                        for (const auto& oferta : lista_oferte) {
                            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                         }
                    }
                    catch (const Exceptii& e) {
                        QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
                    }
                }
            }


            if (!denumire->isChecked() && !destinatie->isChecked() && tip_pret->isChecked()) {
                if (cres->isChecked()) {
                    try {
                        lista->clear();
                        vector<Oferta> lista_oferte = service.service_sortare_tip_pret(false);
                        for (const auto& oferta : lista_oferte) {
                            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                         }
                    }
                    catch (const Exceptii& e) {
                        QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
                    }
                }
                else {
                    try {
                        lista->clear();
                        vector<Oferta> lista_oferte = service.service_sortare_tip_pret(true);
                        for (const auto& oferta : lista_oferte) {
                            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                         }
                    }
                    catch (const Exceptii& e) {
                        QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
                    }
                }
            }
            if (!denumire->isChecked() && destinatie->isChecked() && !tip_pret->isChecked()) {
                if (cres->isChecked()) {
                    try {
                        lista->clear();
                        vector<Oferta> lista_oferte = service.service_sortare_destinatie(false);
                        for (const auto& oferta : lista_oferte) {
                            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                         }
                    }
                    catch (const Exceptii& e) {
                        QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
                    }
                }
                else {
                    try {
                        lista->clear();
                        vector<Oferta> lista_oferte = service.service_sortare_destinatie(true);
                        for (const auto& oferta : lista_oferte) {
                            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                         }
                    }
                    catch (const Exceptii& e) {
                        QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
                    }
                }
            }
        });
    }

};

class Filtreaza_gui: public QWidget {
public:
    Filtreaza_gui(Service_oferta& service): service{service} {
        init_Filtreaza_gui();
        loadOferte();
        initConnect();
    }
private:
    Service_oferta& service;
    QListWidget* lista = new QListWidget();
    QPushButton* buton_filtreaza = new QPushButton("&Filtreaza.");
    QPushButton* buton_exit = new QPushButton("&Exit");
    QComboBox* text1 = new QComboBox();
    QComboBox* text2 = new QComboBox();
    QLabel* label_destinatie = new QLabel("Selectati Destinatia");
    QLabel* label_pret = new QLabel("Selectati Pretul");



    void init_Filtreaza_gui() {
        QHBoxLayout* main_layout = new QHBoxLayout();
        setLayout(main_layout);
        main_layout->addWidget(lista);
        auto vbox = new QHBoxLayout;
        vbox->addWidget(label_destinatie);
        vbox->addWidget(text1);
        auto vbox1 = new QHBoxLayout;
        vbox1->addWidget(label_pret);
        vbox1->addWidget(text2);
        auto button_box = new QVBoxLayout;
        button_box->addWidget(buton_filtreaza);
        button_box->addWidget(buton_exit);

        main_layout->addLayout(vbox);
        main_layout->addLayout(vbox1);
        main_layout->addLayout(button_box);

        vector<Oferta> lista_oferte = service.get_oferte();

        ///populez combo boxul
        for (const auto& oferta : lista_oferte) {
            auto text = QString::fromStdString(oferta.getDestinatie());
            if (text1->findText(text)==-1)
                text1->addItem(text);
        }

        ///populez combo boxul
        for (const auto& oferta : lista_oferte) {
            auto text = std::to_string(oferta.getPret());
            if (text2->findText(text.c_str())==-1)
                text2->addItem(text.c_str());
        }
    }

    void loadOferte() {
        lista->clear();
        vector<Oferta> lista_oferte = service.get_oferte();
        for (const auto& oferta : lista_oferte) {
            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
        }
    }

    void initConnect() {
        QObject::connect(buton_exit, &QPushButton::clicked, [&]() {
            close();
        });

        ///ceva nu merge chiar bine
        QObject::connect(buton_filtreaza, &QPushButton::clicked, [&]() {
            try {
                lista->clear();
                vector<Oferta> lista_oferte = service.service_filtrare(text1->currentText().toStdString(), text2->currentText().toFloat());
                for (const auto& oferta : lista_oferte) {
                     lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                }
            }
            catch (const Exceptii& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });
    }
};


class Cauta_gui: public QWidget {
public:
    Cauta_gui(Service_oferta& service): service{service} {
        init_Cauta_gui();
        loadOferte();
        initConnect();
    }
private:
    Service_oferta& service;
    QListWidget* lista = new QListWidget();
    QFormLayout* form = new QFormLayout;
    QPushButton* buton_destinatie = new QPushButton("&Cauta dupa destinatie.");
    QPushButton* buton_denumire = new QPushButton("&Cauta dupa denumire.");
    QPushButton* buton_tip = new QPushButton("&Cauta dupa tip.");
    QPushButton* buton_pret = new QPushButton("&Cauta dupa pret.");
    QPushButton* buton_exit = new QPushButton("&Exit");
    QComboBox* text1 = new QComboBox();
    QComboBox* text2 = new QComboBox();
    QComboBox* text3 = new QComboBox();
    QComboBox* text4 = new QComboBox();


    void init_Cauta_gui() {
        QHBoxLayout* main_layout = new QHBoxLayout();
        setLayout(main_layout);
        main_layout->addWidget(lista);
        auto vbox = new QVBoxLayout;
        auto button_box = new QVBoxLayout;
        button_box->addWidget(buton_denumire);
        button_box->addWidget(text1);
        button_box->addWidget(buton_destinatie);
        button_box->addWidget(text2);
        button_box->addWidget(buton_tip);
        button_box->addWidget(text3);
        button_box->addWidget(buton_pret);
        button_box->addWidget(text4);
        button_box->addWidget(buton_exit);
        vbox->addLayout(button_box);

        main_layout->addLayout(vbox);

        vector<Oferta> lista_oferte = service.get_oferte();
        for (const auto& oferta : lista_oferte) {
            auto text = QString::fromStdString(oferta.getDenumire());
            if (text1->findText(text)==-1)
                text1->addItem(text);
        }

        for (const auto& oferta : lista_oferte) {
            auto text = QString::fromStdString(oferta.getDestinatie());
            if (text2->findText(text)==-1)
                text2->addItem(text);
        }

        for (const auto& oferta : lista_oferte) {
            auto text = QString::fromStdString(oferta.getTip());
            if (text3->findText(text)==-1)
                text3->addItem(text);
        }

        for (const auto& oferta : lista_oferte) {
            auto text = std::to_string(oferta.getPret());
            if (text4->findText(text.c_str())==-1)
                text4->addItem(text.c_str());
        }
    }

    void loadOferte() {
        lista->clear();
        vector<Oferta> lista_oferte = service.get_oferte();
        for (const auto& oferta : lista_oferte) {
            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
        }
    }

    void initConnect() {
        ///iesire la apasarea butonului
        QObject::connect(buton_exit, &QPushButton::clicked, [&]() {
            close();
        });

        ///cauta dupa denumire
        QObject::connect(buton_denumire, &QPushButton::clicked,[&]() {
            try {
                lista->clear();
                vector<Oferta> lista_oferte = service.service_cautare_denumire(text1->currentText().toStdString());
                for (const auto& oferta : lista_oferte) {
                     lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                }
            }
            catch (const Exceptii& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        ///cauta dupa destinatie
        QObject::connect(buton_destinatie, &QPushButton::clicked,[&]() {
            try {
                lista->clear();
                vector<Oferta> lista_oferte = service.service_cautare_destinatie(text2->currentText().toStdString());
                for (const auto& oferta : lista_oferte) {
                     lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                }
            }
            catch (const Exceptii& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        ///cauta dupa tip
        QObject::connect(buton_tip, &QPushButton::clicked,[&]() {
            try {
                lista->clear();
                vector<Oferta> lista_oferte = service.service_cautare_tip(text3->currentText().toStdString());
                for (const auto& oferta : lista_oferte) {
                     lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
                }
            }
            catch (const Exceptii& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        ///cauta dupa pret
        QObject::connect(buton_pret, &QPushButton::clicked,[&]() {
           try {
               lista->clear();
               vector<Oferta> lista_oferte = service.service_cautare_pret(text4->currentText().toFloat());
               for (const auto& oferta : lista_oferte) {
                    lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
               }
           }
           catch (const Exceptii& e) {
               QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
           }
       });
    }
};

class GUI: public QWidget, public Observer {
public:
    GUI(Service_oferta& service): service{service} {
        service.add_observer(this);
        initGUI();
        loadGUI();
        intiConnect();
    }
private:
    Service_oferta& service;
    QListWidget* lista = new QListWidget();
    QPushButton* buton_adaugare = new QPushButton("&Adauga");
    QPushButton* buton_exit = new QPushButton("&Exit");
    QPushButton* buton_modificare = new QPushButton("&Modifica");
    QPushButton* buton_stergere = new QPushButton("&Sterge");
    QPushButton* buton_undo = new QPushButton("&Undo");
    QPushButton* buton_cautare = new QPushButton("&Cauta oferta");
    QPushButton* buton_filtrare = new QPushButton("&Filtreaza");
    QPushButton* buton_sortare = new QPushButton("&Sortare");
    QPushButton* buton_wishlist = new QPushButton("&Wishlist Crud");
    QPushButton* buton_wishlist_read = new QPushButton("&Wishlist Read Only");
    QLineEdit* id_oferta = new QLineEdit();
    QLineEdit* denumire_oferta = new QLineEdit();
    QLineEdit* destinatie_oferta = new QLineEdit();
    QLineEdit* tip_oferta = new QLineEdit();
    QLineEdit* pret_oferta = new QLineEdit();
    QFormLayout* form = new QFormLayout;
    QPushButton* buton_dinamic = new QPushButton("&Butoane dinamice");
    QLayout* layout_dinamic = new QVBoxLayout;

    void update() override {
        loadGUI();
    }

    void initGUI() {
        QHBoxLayout* main_layout = new QHBoxLayout();
        setLayout(main_layout);
        main_layout->addWidget(lista);
        auto vbox = new QVBoxLayout;
        //auto form = new QFormLayout;
        form->addRow("Id oferta", id_oferta);
        form->addRow("Denumire oferta", denumire_oferta);
        form->addRow("Destinatie oferta", destinatie_oferta);
        form->addRow("Tip oferta", tip_oferta);
        form->addRow("Pret oferta", pret_oferta);
        vbox->addLayout(form);

        auto button_box = new QVBoxLayout;
        button_box->addWidget(buton_adaugare);
        button_box->addWidget(buton_modificare);
        button_box->addWidget(buton_stergere);
        button_box->addWidget(buton_undo);
        button_box->addWidget(buton_cautare);
        button_box->addWidget(buton_filtrare);
        button_box->addWidget(buton_sortare);
        button_box->addWidget(buton_wishlist);
        button_box->addWidget(buton_wishlist_read);
        button_box->addWidget(buton_dinamic);
        button_box->addWidget(buton_exit);
        vbox->addLayout(button_box);
        main_layout->addLayout(vbox);
        main_layout->addLayout(layout_dinamic);
    }

    void loadGUI() {
        lista->clear();
        vector<Oferta> lista_oferte = service.get_oferte();
        for (const auto& oferta : lista_oferte) {
            lista->addItem(QString::fromStdString(std::to_string(oferta.getId()) + " " + oferta.getDenumire() + " " + oferta.getDestinatie() + " " + oferta.getTip() + " " + std::to_string(oferta.getPret())));
        }
    }

    void intiConnect() {
        ///iesire la apasarea butonului
        QObject::connect(buton_exit, &QPushButton::clicked, [&]() {
            close();
        });

        ///adaugare la apasarea butonului
        QObject::connect(buton_adaugare, &QPushButton::clicked, [&]() {
            auto id = id_oferta->text().toStdString();
            auto denumire = denumire_oferta->text().toStdString();
            auto destinatie = destinatie_oferta->text().toStdString();
            auto tip = tip_oferta->text().toStdString();
            auto pret = pret_oferta->text().toStdString();
            try {
                service.service_adauga(std::stoi(id), std::string(denumire), std::string(destinatie), std::string(tip), std::stod(pret));
                loadGUI();
            } catch (const std::exception& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        ///modificare la apasarea butonului
        QObject::connect(buton_modificare, &QPushButton::clicked, [&]() {
            auto id = id_oferta->text().toStdString();
            auto denumire = denumire_oferta->text().toStdString();
            auto destinatie = destinatie_oferta->text().toStdString();
            auto tip = tip_oferta->text().toStdString();
            auto pret = pret_oferta->text().toStdString();
            try {
                service.service_modifica(std::stoi(id), std::string(denumire), std::string(destinatie), std::string(tip), std::stod(pret));
                lista->clear();
                loadGUI();
            } catch (const std::exception& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        ///stergere la apasarea butonului
        QObject::connect(buton_stergere, &QPushButton::clicked, [&]() {
            auto id = id_oferta->text().toStdString();
            auto denumire = denumire_oferta->text().toStdString();
            auto destinatie = destinatie_oferta->text().toStdString();
            auto tip = tip_oferta->text().toStdString();
            auto pret = pret_oferta->text().toStdString();
            try {
                service.service_sterge(std::stoi(id), std::string(denumire), std::string(destinatie), std::string(tip), std::stod(pret));
                loadGUI();
            } catch (const std::exception& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        QObject::connect(buton_undo, &QPushButton::clicked, [&]() {
            try {
                service.service_undo();
                loadGUI();
            } catch (const std::exception& e) {
                QMessageBox::warning(nullptr, "Eroare", QString::fromStdString(e.what()));
            }
        });

        ///completare text cand selectezi ceva din lista
        QObject::connect(lista, &QListWidget::itemSelectionChanged, [&]() {
            auto items = lista->selectedItems();
            if (items.empty()) {
                id_oferta->clear();
                denumire_oferta->clear();
                destinatie_oferta->clear();
                tip_oferta->clear();
                pret_oferta->clear();
            }
            else {
                auto item = items.first();
                id_oferta->setText(item->text().split(" ").at(0));
                denumire_oferta->setText(item->text().split(" ").at(1));
                destinatie_oferta->setText(item->text().split(" ").at(2));
                tip_oferta->setText(item->text().split(" ").at(3));
                pret_oferta->setText(item->text().split(" ").at(4));
            }
        });

        ///cauta oferta
        QObject::connect(buton_cautare, &QPushButton::clicked, [&]() {
            auto cauta_gui = new Cauta_gui(service);
            cauta_gui->show();
        });

        ///filtreaza oferte
        QObject::connect(buton_filtrare, &QPushButton::clicked, [&]() {
            auto filtreaza_gui = new Filtreaza_gui(service);
            filtreaza_gui->show();
        });

        QObject::connect(buton_sortare, &QPushButton::clicked, [&]() {
            auto sortare_gui = new Sortare_gui(service);
            sortare_gui->show();
        });

        QObject::connect(buton_wishlist, &QPushButton::clicked, [&]() {
            auto wishlist_gui = new Wishlist_gui(service);
            wishlist_gui->show();
        });

        QObject::connect(buton_wishlist_read, &QPushButton::clicked, [&]() {
            auto wishlist_read_gui = new Wishlist_read_gui(service);
            wishlist_read_gui->show();
        });

        QObject::connect(buton_dinamic, &QPushButton::clicked, [&]() {
            auto vector = service.service_dictionar_tip();
            for (const auto& [tip, vectorOferte] : vector) {
                QPushButton* btnTip = new QPushButton(QString::fromStdString(tip));
                layout_dinamic->addWidget(btnTip);
            }
        });
    };
};


#endif //GUI_H

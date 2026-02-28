#include <QApplication>
#include <QPushButton>
#include ".//GUI/Gui.h"
#include ".//Service/Service_oferta.h"

int main(int argc, char *argv[]) {
    Repo_oferta repo;
    Validator_oferta validator;
    Cos cos;
    Service_oferta service(repo, validator, cos);
    QApplication a(argc, argv);
    GUI gui(service);
    gui.show();
    return QApplication::exec();
}
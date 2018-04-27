package reports

class Employer {
    String login
    String password

    static constraints = {
        login blank: false, unique: true, maxSize: 30
    }
}

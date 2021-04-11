import pyodbc
from flask import Flask, request, jsonify
from flask_restful import Resource, Api


app = Flask(__name__)
objapi = Api(app)


class getUser(Resource):
    def get(self, strUsername, strPassword, blEditProfile):
        DBfile = "C:\\HOME\\Rik\\Projects\\Python\\OpenUpPY\\openUpDB.accdb"

        conn = pyodbc.connect('Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ='+DBfile)
        cur = conn.cursor()

        SQL = "SELECT Username, Password, FirstName, LastName, ID FROM ClientUserData WHERE UCase(Username) = '" + strUsername.upper() + "'"
        cur.execute(SQL)
        users = cur.fetchall()
        cur.close()
        conn.close()

        userData = {}

        if users:
            if blEditProfile == "true":
                userData["Username"] = strUsername
                userData["Password"] = users[0][1]
                userData["FirstName"] = users[0][2]
                userData["LastName"] = users[0][3]
                userData["ID"] = users[0][4]
                userData["Error"] = ""
                return userData
            else:
                if strPassword == str(users[0][1]):        
                    userData["Username"] = strUsername
                    userData["Password"] = strPassword
                    userData["FirstName"] = users[0][2]
                    userData["LastName"] = users[0][3]
                    userData["ID"] = users[0][4]
                    userData["Error"] = ""
                    return userData

                else:
                    userData["Error"] = "Invalid Password"
                    return userData
        else:
            userData["Error"] = "Invalid Username"
            return userData

class Register(Resource):
    def get(self, strUsername, strPassword, strFirstName, strLastName):
        
        DBfile = "C:\\HOME\\Rik\\Projects\\Python\\OpenUpPY\\openUpDB.accdb"

        conn = pyodbc.connect('Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ='+DBfile)
        cur = conn.cursor()
        message = {}

        SQL = "SELECT Username FROM ClientUserData WHERE UCase(Username) = '" + strUsername.upper() + "'"
        cur.execute(SQL)
        users = cur.fetchall()
        cur.close()
        conn.close()

        if users:
            message["Message"] = "Username Already Exists"
            return jsonify(message)
        
        else:
            conn2 = pyodbc.connect('Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ='+DBfile)
            cur2 = conn2.cursor()
            
            SQL = "INSERT INTO ClientUserData (Username, Password, FirstName, LastName)"
            SQL = SQL + "VALUES ('" + strUsername + "' ,'" + strPassword + "' ,'" 
            SQL = SQL + strFirstName + "' ,'" + strLastName + "')"
            cur2.execute(SQL)
            conn2.commit()
            cur2.close()
            conn2.close()
    
            message["Message"] = "SUCCESS"
            
            return jsonify(message)

class updateProfileClient(Resource):
    def get(self, strUsername, strPassword, strEmail, strFirstName, strLastName, intID):
        
        DBfile = "C:\\HOME\\Rik\\Projects\\Python\\OpenUpPY\\openUpDB.accdb"


                   
        SQL = "UPDATE ClientUserData SET Username = '" + strUsername + "', Password = '" + strPassword + "', Email = '" + strEmail + "',"
        SQL = SQL + " FirstName = '" + strFirstName + "', LastName = '" + strLastName + "' WHERE ID = " + intID

        conn2 = pyodbc.connect('Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ='+DBfile)
        cur2 = conn2.cursor()
        message2 = {}
        
        cur2.execute(SQL)
        conn2.commit()
        cur2.close()
        conn2.close()

        message2["Username"] = strUsername
        message2["Message"] = "SUCCESS"
        
        return jsonify(message2)


class getEvents(Resource):
    def get(self, month, day, year):
        DBfile = "C:\\HOME\\Rik\\Projects\\Python\\OpenUpPY\\openUpDB.accdb"

        conn = pyodbc.connect('Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ='+DBfile)
        cur = conn.cursor()
        date = month + "/" + day +  "/" + year

        SQL = "SELECT EventName, Host, FORMAT(EventTime, 'Medium Time'), Link FROM EventData WHERE FORMAT(EventDate, 'Short Date') = '" + date + "'"
        cur.execute(SQL)
        events = cur.fetchall()
        cur.close()
        conn.close()

        events_arr = []

        if events:
            for i in range (0, len(events)):
                events_dict = {}
                events_dict["EventName"] = events[i][0]
                events_dict["Host"] = events[i][1]
                events_dict["EventTime"] = events[i][2]
                events_dict["Link"] = events[i][3]
                events_dict["Error"] = ""

                events_arr.append(events_dict)
        else:
            events_dict = {}
            events_dict["EventName"] = ""
            events_dict["Host"] = ""
            events_dict["EventTime"] = ""
            events_dict["Link"] = ""
            events_dict["Error"] = "No events found"

            events_arr.append(events_dict)

        return jsonify(events_arr)



objapi.add_resource(getUser, '/getUser/<strUsername>/<strPassword>/<blEditProfile>')
objapi.add_resource(Register, "/Register/<strUsername>/<strPassword>/<strFirstName>/<strLastName>")
objapi.add_resource(updateProfileClient, "/updateProfileClient/<strUsername>/<strPassword>/<strEmail>/<strFirstName>/<strLastName>/<intID>")
objapi.add_resource(getEvents, "/getEvents/<month>/<day>/<year>")

#app.run(debug=True)
app.run(host='0.0.0.0', port=5000)

    






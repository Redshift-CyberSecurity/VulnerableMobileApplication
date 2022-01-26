from flask import Flask, render_template
import base64

app = Flask(__name__)

@app.route("/")
def index():
    return "RS_SERVER"

@app.route('/test')
def test():
    return render_template('test.html')

@app.route('/buycredit/cardname/<string:card_name>')
def buycredit(card_name):
    base64_message = card_name
    base64_bytes = base64_message.encode('ascii')
    message_bytes = base64.b64decode(base64_bytes)
    card_name = message_bytes.decode('ascii')
    print(card_name)
    return render_template('BuyCredits.html', fcardName=card_name)


@app.route("/requestcredits/<int:numCredits>")
def requestcredits(numCredits):
    return str(numCredits)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
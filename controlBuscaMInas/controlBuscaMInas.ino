int upPin = 3;
int downPin = 4;
int leftPin = 5;
int rightPin = 6;
int clickPin = 8;
int rClickPin=9;
int ledPin = 7;
int buzzer=10; 

void setup() {
  Serial.begin(9600);
  pinMode(upPin, INPUT_PULLUP);
  pinMode(downPin, INPUT_PULLUP);
  pinMode(leftPin, INPUT_PULLUP);
  pinMode(rightPin, INPUT_PULLUP);
  pinMode(clickPin, INPUT_PULLUP);
  pinMode(rClickPin, INPUT_PULLUP);
  pinMode(ledPin, OUTPUT);
  pinMode(buzzer,OUTPUT);
}

void loop() {
  while (Serial.available() > 0) {
    char serialChar = Serial.read();
    if (serialChar == 'B') {
      digitalWrite(ledPin, HIGH);
      delay(200);
    }
    if (serialChar=='N'){
      digitalWrite(ledPin, LOW);
      delay(200);      
    }
    if (serialChar=='S'){
      tone(buzzer,2500,150);
      delay(50);
      tone(buzzer,4000,150);
      delay(300);      
    }
    if (serialChar=='M'){
      tone(buzzer,500,1000);
      delay(200);      
    }     
        
  }

  if (!digitalRead(upPin)) {
    Serial.write('A');
    delay(250);
  }
  if (!digitalRead(downPin)) {
    Serial.write('T');
    delay(250);
  }
  if (!digitalRead(leftPin)) {
    Serial.write('I');
    delay(250);
  }
  if (!digitalRead(rightPin)) {
    Serial.write('B');
    delay(250);
  }
  if (!digitalRead(clickPin)) {
    Serial.write('L');
    delay(250);
  }
  if (!digitalRead(rClickPin)) {
    Serial.write('R');
    delay(250);
  }
}



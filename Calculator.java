import  javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class Calculator implements ActionListener{

    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] operationButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, eqButton, delButton, clrButton, negButton;
    JPanel panel;
    //ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("5566.jpg"));
    ImageIcon logo = new ImageIcon("res/5566.png");
    Font myFont = new Font("Times New Roman", Font.BOLD, 30);

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    Calculator()
    {

        Color operatorColor = new Color(236, 40, 94);
        Color numberColor = new Color(171, 23, 201);
        Color textFieldColor = new Color(70, 236, 241);



        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);
        frame.setIconImage(logo.getImage());
        frame.setLocationRelativeTo(null);

        ImageIcon bg = new ImageIcon("Gradient.jpg");
        JLabel background = new JLabel(bg);
        background.setSize( 420, 550);
        frame.add(background);


        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(true);
        textField.setBackground(textFieldColor);

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        eqButton = new JButton("=");
        delButton = new JButton("DEL");
        clrButton = new JButton("CLR");
        negButton = new JButton("(-)");

        operationButtons[0] = addButton;
        operationButtons[1] = subButton;
        operationButtons[2] = mulButton;
        operationButtons[3] = divButton;
        operationButtons[4] = decButton;
        operationButtons[5] = eqButton;
        operationButtons[6] = delButton;
        operationButtons[7] = clrButton;
        operationButtons[8] = negButton;


        for(int i = 0; i < 9; i++)
        {
            operationButtons[i].addActionListener(this);
            operationButtons[i].setFont(myFont);
            operationButtons[i].setFocusable(false);
            operationButtons[i].setBackground(operatorColor);
        }


        for(int i = 0; i < 10; i++)
        {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(numberColor);

        }

        negButton.setBounds(50, 430, 100, 50 );
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(eqButton);
        panel.add(divButton);


        frame.add(panel);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(negButton);
        frame.add(textField);
        frame.setVisible(true);
    }
    public static void main(String[] args)
    {
        Calculator calc = new Calculator();

    }

    public abstract class Button extends JButton implements MouseListener
    {
        private boolean rounded;
        private boolean backgroundPainted;
        private boolean linePainted;
        private boolean entered;
        private boolean pressed;

        private Color enteredColor;
        private Color pressedColor;
        private Color gradientBackground;
        private Color gradientLineColor;
        private Color lineColor;

        public Button()
        {
            super();
            rounded = false;
            backgroundPainted = true;
            linePainted = true;
            entered = false;
            pressed = false;

            enteredColor = getBackground().brighter();
            pressedColor = getBackground().darker();
            lineColor = Color.BLACK;
            setContentAreaFilled(false);
            setFocusPainted(false);
        }

        @Override
        public void setBackground(Color bg) {
            super.setBackground(bg);
            enteredColor = bg.brighter();
            pressedColor = bg.darker();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape s = (rounded) ? new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, getHeight() - 2, getHeight() - 2) : new Rectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2);

            if(backgroundPainted || (pressed && !backgroundPainted))
            {
                if(gradientBackground == null)
                {
                    g2.setColor(color());
                }
                else
                {
                    GradientPaint paint = new GradientPaint(0, 0, getBackground(), getWidth(), getHeight(), gradientBackground);
                    g2.setPaint(paint);
                }
                g2.fill(s);
            }
            if(linePainted)
            {
                if(gradientLineColor == null)
                {
                    g2.setColor(isEnabled() ? lineColor : new Color(204, 204, 204));
                }
                else
                {
                    GradientPaint paint = new GradientPaint(0, 0, lineColor, getWidth(), getHeight(), gradientLineColor);
                    g2.setPaint(paint);
                }
                g2.draw(s);
            }
            super.paintComponent(g);
        }

        private Color color()
        {
            if (!isEnabled())
            {
                return new Color(204, 204, 204);
            }
            Color temp = getBackground();
            if(pressed)
            {
                return  pressedColor;
            }
            if(entered)
            {
                return enteredColor;
            }
            return temp;
        }


        public void setRounded(boolean rounded) {
            this.rounded = rounded;
        }

        public void setBackgroundPainted(boolean backgroundPainted) {
            this.backgroundPainted = backgroundPainted;
        }

        public void setLinePainted(boolean linePainted) {
            this.linePainted = linePainted;
        }

        public void setEnteredColor(Color enteredColor) {
            this.enteredColor = enteredColor;
        }

        public void setPressedColor(Color pressedColor) {
            this.pressedColor = pressedColor;
        }

        public void setGradientBackground(Color gradientBackground) {
            this.gradientBackground = gradientBackground;
        }

        public void setLineColor(Color lineColor) {
            this.lineColor = lineColor;
        }


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            pressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            entered = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            entered = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < 10; i++)
        {
            if(e.getSource() == numberButtons[i])
            {
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
        if(e.getSource() == decButton)
        {
            textField.setText(textField.getText().concat("."));
        }

        if(e.getSource() == addButton)
        {
           num1 = Double.parseDouble(textField.getText());
           operator = '+';
           textField.setText("");
        }

        if(e.getSource() == subButton)
        {
            num1 = Double.parseDouble(textField.getText());
            operator = '-';
            textField.setText("");
        }

        if(e.getSource() == mulButton)
        {
            num1 = Double.parseDouble(textField.getText());
            operator = '*';
            textField.setText("");
        }

        if(e.getSource() == divButton)
        {
            num1 = Double.parseDouble(textField.getText());
            operator = '/';
            textField.setText("");
        }

        if(e.getSource() == eqButton)
        {
            num2 = Double.parseDouble(textField.getText());

            if (operator == '+')
            {
                result = num1 + num2;
            }
            else if(operator == '-')
            {
                result = num1 - num2;
            }
            else if(operator == '*')
            {
                result = num1 * num2;
            }
            else if(operator == '/')
            {
                result = num1 / num2;
            }

            textField.setText(String.valueOf(result));
            num1 = result;
        }

        if(e.getSource() == clrButton)
        {
            textField.setText("");
        }

        if(e.getSource() == delButton)
        {
            String currentString = textField.getText();
            textField.setText("");
            for(int i = 0; i < currentString.length() - 1; i++)
            {
                textField.setText(textField.getText() + currentString.charAt(i));
            }
        }

        if(e.getSource() == negButton)
        {
            double temp = Double.parseDouble(textField.getText());
            temp *= -1;
            textField.setText(String.valueOf(temp));
        }
    }
}

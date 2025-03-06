package frontDesign;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class AutoSuggestSearchField extends JTextField {
    private float location;
    private boolean show;
    private String labelText = "Search ...";
    private Color lineColor = new Color(33, 155, 216);
    private boolean mouseOver = false;
    private JPopupMenu suggestionPopup;
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;

    private SearchFieldListener searchFieldListener;

    public void setSearchFieldListener(SearchFieldListener listener) {
        this.searchFieldListener = listener;
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedValue = suggestionList.getSelectedValue();
                    setText(selectedValue);
                    suggestionPopup.setVisible(false);

                    if (searchFieldListener != null) {
                        searchFieldListener.onSuggestionSelected(selectedValue);
                    }
                }
            }
        });
    }

    public AutoSuggestSearchField(List<String> suggestions, int top, int bottom, int left, int right) {
        setBorder(new EmptyBorder(top, left, bottom, right));
        listModel = new DefaultListModel<>();

        // get foundings from key Listener
        suggestionList = new JList<>(listModel);
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, Integer.MAX_VALUE));
        HoverListCellRenderer renderer = new HoverListCellRenderer();
        suggestionList.setCellRenderer(renderer);
        suggestionPopup = new JPopupMenu();
        suggestionPopup.setFocusable(false);
        suggestionPopup.setBorder(BorderFactory.createEmptyBorder(3,2,50,getWidth()));
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(3, 0, 10, 10));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        suggestionPopup.add(scrollPane);

        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setText(suggestionList.getSelectedValue());
                    suggestionPopup.setVisible(false);
                }
            }
        });

        suggestionList.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = suggestionList.locationToIndex(e.getPoint());
                renderer.setHoverIndex(index);
                suggestionList.repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = getText();
                listModel.clear();
                if (!text.isEmpty()) {
                    for (String suggestion : suggestions) {
                        if (suggestion.toLowerCase().contains(text.toLowerCase())) {
                            listModel.addElement(suggestion);
                        }
                    }
                    if (!listModel.isEmpty()) {
                        suggestionPopup.show(AutoSuggestSearchField.this, 0, getHeight());
                    } else {
                        suggestionPopup.setVisible(false);
                    }
                } else {
                    suggestionPopup.setVisible(false);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                show = true;
                animateHint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                show = false;
                suggestionPopup.setVisible(false);
                animateHint();
            }
        });
    }

    public AutoSuggestSearchField(List<String> suggestions) {
        this(suggestions, 10, 15, 3, 5);
    }

    public AutoSuggestSearchField(List<String> suggestions, int top, int bottom, int left, int right,
            String labelText) {
        this(suggestions, top, bottom, left, right);
        this.labelText = labelText;
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if (mouseOver) {
            g2.setColor(Color.black);
        } else {
            g2.setColor(new Color(76, 204, 255));
        }
        g2.fillRect(2, ((height / 2) + 8), (width * 3 / 4), 1);
        if (getText().isEmpty()) {
            createHintText(g2);
        }

        createLineStyle(g2);
        g2.dispose();
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics fnt = g2.getFontMetrics();
        Rectangle2D r2 = fnt.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom + 5;
        double textY = (height - r2.getHeight() / 3);
        g2.drawString(labelText, in.right, (int) (in.top + textY + fnt.getAscent() * (1 - location)));
    }

    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            double size = 1, x = (width - size) - 2;
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }
    }

    private void animateHint() {
        Timer timer = new Timer(10, e -> {
            if (show) {
                location += 0.05f;
                if (location > 1.3f) {
                    location = 1.3f;
                    ((Timer) e.getSource()).stop();
                }
            } else {
                location -= 0.05f;
                if (location < 0) {
                    location = 0;
                    ((Timer) e.getSource()).stop();
                }
            }
            repaint();
        });
        timer.start();
    }
}

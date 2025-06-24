package org.yetyman.controls.ninepatch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.yetyman.controls.GridHelper;
import org.yetyman.controls.selection.CycleButton;

import java.util.ArrayList;
import java.util.List;

public class NinePatchTestController extends GridPane {
    private final Slider ninePatchPatchSizesH = new Slider(0,100, 5);
    private final Slider ninePatchPatchSizesV = new Slider(0,100, 5);
    private final Slider ninePatchContentSizesH = new Slider(0,100, 5);
    private final Slider ninePatchContentSizesV = new Slider(0,100, 5);
    private final Slider ninePatchTitleMarginH = new Slider(0,100, 10);
    private final Slider ninePatchTitleMarginV = new Slider(0,100, 10);
    private final Slider ninePatchPatchInsetsH = new Slider(0,100, 0);
    private final Slider ninePatchPatchInsetsV = new Slider(0,100, 0);
    private final Slider gridMarginSliderH = new Slider(0,100, 10);
    private final Slider gridMarginSliderV = new Slider(0,100, 0);
    private final Slider ninePatchContentPaddingH = new Slider(0,100, 0);
    private final Slider ninePatchContentPaddingV = new Slider(0,100, 0);
    private final NinePatchPane ninePatch = new NinePatchPane();

    private final Slider imgScaleSlider = new Slider(.01,10, .25);
    private final CycleButton<BackgroundRepeat> imageRepeatHCB = new CycleButton<>();
    private final CycleButton<BackgroundRepeat> imageRepeatVCB = new CycleButton<>();
    private final TextField titleText = new TextField();


    public NinePatchTestController() {
        super();
    
        Image im = new Image("/9PatchSample.png");
        ninePatch.setBackground(im);
        ninePatch.setTitleBackground(im);

        GridPane gp = new GridPane();
        GridHelper.size(gp, 3,3);
        GridHelper.layout(gp,
            btn(HPos.LEFT, VPos.TOP),btn(HPos.CENTER, VPos.TOP),btn(HPos.RIGHT, VPos.TOP),
            btn(HPos.LEFT, VPos.CENTER),btn(HPos.CENTER, VPos.CENTER),btn(HPos.RIGHT, VPos.CENTER),
            btn(HPos.LEFT, VPos.BOTTOM),btn(HPos.CENTER, VPos.BOTTOM),btn(HPos.RIGHT, VPos.BOTTOM)
        );

        GridHelper.size(this, 3,9);
        GridHelper.layout(this,
                titleText,                                       ninePatchPatchSizesH,              gp,
                btn("Select Img", this::selectImage),               ninePatchPatchSizesV,              gp,
                btn("Select Title Img", this::selectTitleImage),    new Label("Img Patch Sizes"), gp,
                ninePatchPatchInsetsH,                                  ninePatch,                         ninePatchContentPaddingH,
                ninePatchPatchInsetsV,                                  ninePatch,                         ninePatchContentPaddingV,
                new Label("Patch Insets"),                         ninePatch,                         new Label("Content Padding"),
                btn("Toggle Borders", this::toggleBorders),         ninePatchContentSizesH,            imageRepeatHCB,
                null,                                                   ninePatchContentSizesV,            imageRepeatVCB,
                null,                                                   new Label("Corner Sizes"),    imgScaleSlider
                );

        getColumnConstraints().get(0).setMaxWidth(USE_PREF_SIZE);
        getColumnConstraints().get(2).setMaxWidth(USE_PREF_SIZE);

        getRowConstraints().get(0).setMaxHeight(USE_PREF_SIZE);
        getRowConstraints().get(1).setMaxHeight(USE_PREF_SIZE);
        getRowConstraints().get(2).setMaxHeight(USE_PREF_SIZE);
        getRowConstraints().get(6).setMaxHeight(USE_PREF_SIZE);
        getRowConstraints().get(7).setMaxHeight(USE_PREF_SIZE);
        getRowConstraints().get(8).setMaxHeight(USE_PREF_SIZE);

        ninePatch.setTitle("hi i'm yety!");
        ninePatch.setTitleContent(new Label("Hi there"));

        titleText.textProperty().bindBidirectional(ninePatch.titleProperty);

        imageRepeatHCB.items.setAll(new ArrayList<>(List.of(BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND)));
        imageRepeatVCB.items.setAll(new ArrayList<>(List.of(BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND)));

        ninePatch.backgroundHRepeatProperty.bindBidirectional(imageRepeatHCB.selectedItem);
        ninePatch.backgroundVRepeatProperty.bindBidirectional(imageRepeatVCB.selectedItem);

        ninePatch.imageScaleProperty.bindBidirectional(imgScaleSlider.valueProperty());

        GridPane.setFillHeight(ninePatch, true);
        GridPane.setFillWidth(ninePatch, true);

        VBox v1;
        ninePatch.contentChildren.add(v1 = new VBox(new Slider(),new Slider(),new Slider(),new Slider(),new Slider()));
        v1.setFillWidth(true);
        v1.setAlignment(Pos.CENTER);
        ninePatch.getContentPane().setAlignment(Pos.CENTER);
        ninePatch.getContentPane().setAlignment(Pos.CENTER);


        ninePatchPatchSizesH.valueProperty().addListener(observable -> {
            var i = ninePatch.getImgPatchSizes();
            ninePatch.setImgPatchSizes(new Insets(i.getTop(), ninePatchPatchSizesH.getValue(), i.getBottom(), ninePatchPatchSizesH.getValue()));
        });
        ninePatchPatchSizesV.valueProperty().addListener(observable -> {
            var i = ninePatch.getImgPatchSizes();
            ninePatch.setImgPatchSizes(new Insets(ninePatchPatchSizesV.getValue(), i.getRight(), ninePatchPatchSizesV.getValue(), i.getLeft()));
        });

        ninePatchPatchInsetsH.valueProperty().addListener(observable -> {
            var i = ninePatch.getImgPatchInsets();
            ninePatch.setImgPatchInsets(new Insets(i.getTop(), ninePatchPatchInsetsH.getValue(), i.getBottom(), ninePatchPatchInsetsH.getValue()));
        });
        ninePatchPatchInsetsV.valueProperty().addListener(observable -> {
            var i = ninePatch.getImgPatchInsets();
            ninePatch.setImgPatchInsets(new Insets(ninePatchPatchInsetsV.getValue(), i.getRight(), ninePatchPatchInsetsV.getValue(), i.getLeft()));
        });

        ninePatchContentSizesH.valueProperty().addListener(observable -> {
            var i = ninePatch.controlPatchSizesProperty.get();
            ninePatch.setContentPatchSizes(new Insets(i.getTop(), ninePatchContentSizesH.getValue(), i.getBottom(), ninePatchContentSizesH.getValue()));
        });
        ninePatchContentSizesV.valueProperty().addListener(observable -> {
            var i = ninePatch.controlPatchSizesProperty.get();
            ninePatch.setContentPatchSizes(new Insets(ninePatchContentSizesV.getValue(), i.getRight(), ninePatchContentSizesV.getValue(), i.getLeft()));
        });

        ninePatchTitleMarginH.valueProperty().addListener(observable -> {
            var i = ninePatch.getTitleMargins();
            ninePatch.setTitleMargins(new Insets(i.getTop(), ninePatchTitleMarginH.getValue(), i.getBottom(), ninePatchTitleMarginH.getValue()));
        });
        ninePatchTitleMarginV.valueProperty().addListener(observable -> {
            var i = ninePatch.getTitleMargins();
            ninePatch.setTitleMargins(new Insets(ninePatchTitleMarginV.getValue(), i.getRight(), ninePatchTitleMarginV.getValue(), i.getLeft()));
        });
        

        ninePatchContentPaddingH.valueProperty().addListener(observable -> {
            var i = ninePatch.getContentPadding();
            ninePatch.setContentPadding(new Insets(i.getTop(), ninePatchContentPaddingH.getValue(), i.getBottom(), ninePatchContentPaddingH.getValue()));
        });
        ninePatchContentPaddingV.valueProperty().addListener(observable -> {
            var i = ninePatch.getContentPadding();
            ninePatch.setContentPadding(new Insets(ninePatchContentPaddingV.getValue(), i.getRight(), ninePatchContentPaddingV.getValue(), i.getLeft()));
        });

        gridMarginSliderH.valueProperty().addListener(observable -> {
            var i = ninePatch.getContentMargins();
            GridPane.setMargin(ninePatch, new Insets(i.getTop(), gridMarginSliderH.getValue(), i.getBottom(), gridMarginSliderH.getValue()));
        });
        gridMarginSliderV.valueProperty().addListener(observable -> {
            var i = ninePatch.getContentMargins();
            GridPane.setMargin(ninePatch, new Insets(gridMarginSliderV.getValue(), i.getRight(), gridMarginSliderV.getValue(), i.getLeft()));
        });
    }

    private Button btn(HPos hPos, VPos vPos) {
        Button b = new Button();
        String lbl = "";

        lbl += switch (hPos) {
            case LEFT -> "L";
            case CENTER -> "C";
            case RIGHT -> "R";
        };
        lbl += switch (vPos) {
            case TOP -> "T";
            case CENTER, BASELINE -> "C";
            case BOTTOM -> "B";
        };

        b.setText(lbl);

        b.setOnAction(e -> {
            ninePatch.setTitleHPos(hPos);
            ninePatch.setTitleVPos(vPos);
        });

        return b;
    }
    private Button btn(String txt, EventHandler<ActionEvent> action) {
        Button b = new Button(txt);
        b.setOnAction(action);
        return b;
    }

    void selectImage(ActionEvent event) {
        var file = new FileChooser().showOpenDialog(this.getScene().getWindow());
        if(file!=null) {
            String path = file.toURI().toString();
            var img = new Image(path);
            ninePatch.setBackground(img);
            ninePatch.setBackground(img);
        }
    }
    
    void selectTitleImage(ActionEvent event) {
        var file = new FileChooser().showOpenDialog(this.getScene().getWindow());
        if(file!=null) {
            String path = file.toURI().toString();
            var img = new Image(path);
            ninePatch.setTitleBackground(img);
            ninePatch.setTitleBackground(img);
        }
    }
    
    void toggleBorders(ActionEvent event) {
        if (ninePatch.getPatchDebugBorder() == null) {
            ninePatch.setPatchDebugBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, new CornerRadii(4), BorderStroke.THIN)));
            ninePatch.setPatchDebugBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, new CornerRadii(4), BorderStroke.THIN)));
        }else{
            ninePatch.setPatchDebugBorder(null);
            ninePatch.setPatchDebugBorder(null);
        }
    }
}

package org.yetyman.controls.ninepatch;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.css.converter.SizeConverter;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yetyman.controls.CssHelper;

public class NinePatchPane extends GridPane {
    private static Logger log = LoggerFactory.getLogger(NinePatchPane.class);

    /**
     * Insets describing the number of pixels of patch border on any side.
     * This in conjunction with patchScale determines the number of pixels of an image which are sampled for the border
     */
    public final ObjectProperty<Insets> controlPatchSizesProperty
            = CssHelper.createCssProperty(this, "-fx-patch-size", InsetsConverter.getInstance(), Insets.EMPTY);

    /**
     * Insets describing the number of pixels of imgBorder border on any side.
     * This in conjunction with imgBorderScale determines the number of pixels of an image which are sampled for the border
     */
    public final ObjectProperty<Insets> imgPatchSizesProperty
            = CssHelper.createCssProperty(this, "-fx-img-patch", InsetsConverter.getInstance(), Insets.EMPTY);

    /**
     * Insets describing the number of pixels to inset from the borders of the src image on any side.
     */
    public final ObjectProperty<Insets> imgPatchInsetsProperty
            = CssHelper.createCssProperty(this, "-fx-img-patch-insets", InsetsConverter.getInstance(), Insets.EMPTY);

    /**
     * Insets describing the number of pixels of patch border on any side.
     * This in conjunction with patchScale determines the number of pixels of an image which are sampled for the border
     */
    public final ObjectProperty<Number> imageScaleProperty = CssHelper.createCssProperty(this, "-fx-image-scale", SizeConverter.getInstance(), 2);

    private final Label debugLabel;

    public final Insets getContentPatchSizes(){
      return controlPatchSizesProperty.get();
    }
    public final void setContentPatchSizes(Insets val){
      controlPatchSizesProperty.set(val);
    }

    public final Insets getImgPatchSizes(){
      return imgPatchSizesProperty.get();
    }
    public final void setImgPatchSizes(Insets val){
        imgPatchSizesProperty.set(val);
    }

    public final Insets getImgPatchInsets(){
      return imgPatchInsetsProperty.get();
    }
    public final void setImgPatchInsets(Insets val){
        imgPatchInsetsProperty.set(val);
    }

    public SimpleObjectProperty<Insets> contentPaddingProperty = new SimpleObjectProperty<>(new Insets(5));
    public Insets getContentPadding(){
      return contentPaddingProperty.get();
    }

    public void setContentPadding(Insets val){
      contentPaddingProperty.set(val);
    }

    public SimpleObjectProperty<Insets> titleMarginsProperty = new SimpleObjectProperty<>(new Insets(0));
    public Insets getTitleMargins(){
        return titleMarginsProperty.get();
    }

    public void setTitleMargins(Insets val){
        titleMarginsProperty.set(val);
    }

    public SimpleObjectProperty<String> titleProperty = new SimpleObjectProperty<>("Hi I'm Yety!");
    public String getTitle(){
      return titleProperty.get();
    }

    public void setTitle(String val){
      titleProperty.set(val);
    }
    public SimpleObjectProperty<HPos> titleHPosProperty = new SimpleObjectProperty<>(HPos.LEFT);
    public HPos getTitleHPos(){
      return titleHPosProperty.get();
    }

    public void setTitleHPos(HPos val){
      titleHPosProperty.set(val);
    }
    public SimpleObjectProperty<VPos> titleVPosProperty = new SimpleObjectProperty<>(VPos.BOTTOM);
    public VPos getTitleVPos(){
      return titleVPosProperty.get();
    }

    public void setTitleVPos(VPos val){
      titleVPosProperty.set(val);
    }

    public SimpleObjectProperty<Image> srcImgProperty = new SimpleObjectProperty<>(null);
    public Image getSrcImg(){
      return srcImgProperty.get();
    }

    public void setSrcImg(Image val){
      srcImgProperty.set(val);
    }
    public SimpleObjectProperty<Image> titleImgProperty = new SimpleObjectProperty<>(null);
    public Image getTitleImg(){
      return titleImgProperty.get();
    }

    public void setTitleBackground(Image val){
      titleImgProperty.set(val);
    }
    public SimpleObjectProperty<BackgroundRepeat> backgroundHRepeatProperty = new SimpleObjectProperty<>(BackgroundRepeat.NO_REPEAT);
    public BackgroundRepeat getBackgroundHRepeat(){
      return backgroundHRepeatProperty.get();
    }

    public void setBackgroundHRepeat(BackgroundRepeat val){
      backgroundHRepeatProperty.set(val);
    }
    public SimpleObjectProperty<BackgroundRepeat> backgroundVRepeatProperty = new SimpleObjectProperty<>(BackgroundRepeat.NO_REPEAT);
    public BackgroundRepeat getBackgroundVRepeat(){
      return backgroundVRepeatProperty.get();
    }

    public void setBackgroundVRepeat(BackgroundRepeat val){
      backgroundVRepeatProperty.set(val);
    }

    public final ObjectProperty<Node> titleContent = new SimpleObjectProperty<>();
    public void setTitleContent(Node content) { titleContent.set(content); }
    public Node getTitleContent() { return titleContent.get(); }

    public SimpleObjectProperty<Border> patchDebugBorderProperty = new SimpleObjectProperty<>(null);
    public Border getPatchDebugBorder(){
        return patchDebugBorderProperty.get();
    }

    public void setPatchDebugBorder(Border val){
        patchDebugBorderProperty.set(val);
    }

    public SimpleObjectProperty<Insets> contentMarginsProperty = new SimpleObjectProperty<>(){
        @Override
        public Insets get() {
            super.get();
            return GridPane.getMargin(patchGrid);
        }

        @Override
        public void set(Insets newValue) {
            GridPane.setMargin(patchGrid, newValue);
            super.set(newValue);
        }
    };
    public Insets getContentMargins(){
        return contentMarginsProperty.get();
    }

    public void setContentMargins(Insets val){
        contentMarginsProperty.set(val);
    }



    GridPane patchGrid = new GridPane();

    Pane tlPane = new Pane();
    Pane tPane = new Pane();
    Pane trPane = new Pane();
    Pane rPane = new Pane();
    Pane brPane = new Pane();
    Pane bPane = new Pane();
    Pane blPane = new Pane();
    Pane lPane = new Pane();
    Pane cPane = new Pane();

    Image tl = null;
    Image tSrc = null;
    Image tr = null;
    Image rSrc = null;
    Image br = null;
    Image bSrc = null;
    Image bl = null;
    Image lSrc = null;
    Image cSrc = null;

    GridPane contentPane = new GridPane();

    ColumnConstraints pColumn1 = new ColumnConstraints(),
            pColumn2 = new ColumnConstraints(),
            pColumn3 = new ColumnConstraints();
    RowConstraints pRow1 = new RowConstraints(),
            pRow2 = new RowConstraints(),
            pRow3 = new RowConstraints();

    public ObservableList<Node> contentChildren = null;

    public NinePatchPane(){
        patchGrid.getColumnConstraints().addAll(pColumn1, pColumn2, pColumn3);
        patchGrid.getRowConstraints().addAll(pRow1, pRow2, pRow3);

        pColumn1.setFillWidth(true);
        pColumn1.setHgrow(Priority.NEVER);
        pColumn3.setFillWidth(true);
        pColumn3.setHgrow(Priority.NEVER);

        pRow1.setFillHeight(true);
        pRow1.setVgrow(Priority.NEVER);
        pRow3.setFillHeight(true);
        pRow3.setVgrow(Priority.NEVER);

        pColumn2.setFillWidth(true);
        pColumn2.setHgrow(Priority.ALWAYS);

        pRow2.setFillHeight(true);
        pRow2.setVgrow(Priority.ALWAYS);

        contentChildren = contentPane.getChildren();
        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        addAt(this, patchGrid, 0 ,0);

        patchGrid.minWidth(Region.USE_PREF_SIZE);
        patchGrid.maxWidth(Region.USE_PREF_SIZE);
        patchGrid.prefHeight(Region.USE_COMPUTED_SIZE);
        patchGrid.prefWidth(Region.USE_COMPUTED_SIZE);
        patchGrid.minHeight(Region.USE_PREF_SIZE);
        patchGrid.maxHeight(Region.USE_PREF_SIZE);
        contentPane.minWidth(Region.USE_PREF_SIZE);
        contentPane.maxWidth(Region.USE_PREF_SIZE);
        contentPane.prefHeight(Region.USE_COMPUTED_SIZE);
        contentPane.prefWidth(Region.USE_COMPUTED_SIZE);
        contentPane.minHeight(Region.USE_PREF_SIZE);
        contentPane.maxHeight(Region.USE_PREF_SIZE);

        if(getColumnConstraints().isEmpty())
            getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.CENTER, true));
        else
            getColumnConstraints().set(0, new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.CENTER, true));

        if(getRowConstraints().isEmpty())
            getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        else
            getRowConstraints().set(0, new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));

        contentPane.setBackground(null);
        patchGrid.setBackground(null);


        patchGrid.borderProperty().bindBidirectional(patchDebugBorderProperty);
        tlPane.borderProperty().bindBidirectional(patchDebugBorderProperty);
        brPane.borderProperty().bindBidirectional(patchDebugBorderProperty);

        addAt(patchGrid, tlPane, 0,0);
        addAt(patchGrid, tPane, 1,0);
        addAt(patchGrid, trPane, 2,0);
        addAt(patchGrid, rPane, 2,1);
        addAt(patchGrid, brPane, 2,2);
        addAt(patchGrid, bPane, 1,2);
        addAt(patchGrid, blPane, 0,2);
        addAt(patchGrid, lPane, 0,1);
        addAt(patchGrid, cPane, 1,1);

        addAt(patchGrid, contentPane, 0 ,0);
        GridPane.setColumnSpan(contentPane, GridPane.REMAINING);
        GridPane.setRowSpan(contentPane, GridPane.REMAINING);
        GridPane.setFillHeight(contentPane, true);
        GridPane.setFillWidth(contentPane, true);

        controlPatchSizesProperty.addListener(new ChangeListener<Insets>() {
            @Override
            public void changed(ObservableValue<? extends Insets> observable, Insets oldValue, Insets newValue) {

                pRow1.setMinHeight(controlPatchSizesProperty.get().getTop());
                pRow1.setPrefHeight(controlPatchSizesProperty.get().getTop());
                pRow1.setMaxHeight(controlPatchSizesProperty.get().getTop());

                pRow3.setMinHeight(controlPatchSizesProperty.get().getBottom());
                pRow3.setPrefHeight(controlPatchSizesProperty.get().getBottom());
                pRow3.setMaxHeight(controlPatchSizesProperty.get().getBottom());


                pColumn1.setMinWidth(controlPatchSizesProperty.get().getTop());
                pColumn1.setPrefWidth(controlPatchSizesProperty.get().getTop());
                pColumn1.setMaxWidth(controlPatchSizesProperty.get().getTop());

                pColumn3.setMinWidth(controlPatchSizesProperty.get().getBottom());
                pColumn3.setPrefWidth(controlPatchSizesProperty.get().getBottom());
                pColumn3.setMaxWidth(controlPatchSizesProperty.get().getBottom());

                cutImage();
            }
        });

        imgPatchSizesProperty.addListener((observable, oldValue, newValue) -> {
            cutImage();
        });
        imgPatchInsetsProperty.addListener((observable, oldValue, newValue) -> {
            cutImage();
        });

        imageScaleProperty.addListener((s,a,b)->{
            cutImage();
        });

        contentPaddingProperty.bindBidirectional(contentPane.paddingProperty());

        backgroundHRepeatProperty.addListener((observable, oldValue, newValue) -> cutImage());
        backgroundVRepeatProperty.addListener((observable, oldValue, newValue) -> cutImage());

        srcImgProperty.addListener(observable -> setBackground(srcImgProperty.get()));

        contentPaddingProperty.set(new Insets(7));
        controlPatchSizesProperty.set(new Insets(10));
        GridPane.setMargin(patchGrid, Insets.EMPTY);
        setMinSize(30, 30);

        titleContent.addListener((s,a,b)->{
            if(a != null)
                getChildren().remove(a);
            if(b != null) {
                Node titleNode = titleContent.get();
                addAt(this, titleNode, 0, 0);

                GridPane.setHgrow(titleNode, Priority.NEVER);
                GridPane.setVgrow(titleNode, Priority.NEVER);
                GridPane.setFillHeight(titleNode, false);
                GridPane.setFillWidth(titleNode, false);
            }
        });

        debugLabel = new Label();
        getChildren().add(debugLabel);

        titleMarginsProperty.addListener((o, a, b) -> { if(titleContent.get()!=null) GridPane.setMargin(titleContent.get(), titleMarginsProperty.get()); });
        titleHPosProperty.addListener((o, a, b) -> { if(titleContent.get()!=null) GridPane.setHalignment(titleContent.get(), titleHPosProperty.get()); });
        titleVPosProperty.addListener((o, a, b) -> { if(titleContent.get()!=null) GridPane.setValignment(titleContent.get(), titleVPosProperty.get()); });
    }

    public GridPane getContentPane(){
        return contentPane;
    }

    private void cutImage() {
        if(srcImgProperty.get()==null) return;

        try {
            PixelReader reader = srcImgProperty.get().getPixelReader();

            Insets i = controlPatchSizesProperty.get();
            Insets srcI = imgPatchSizesProperty.get();
            Insets imgInsets = imgPatchInsetsProperty.get();

            double scale = imageScaleProperty.get().doubleValue();

            int lWidth = (int) Math.ceil(i.getLeft());
            int rWidth = (int) Math.ceil(i.getRight());
            int tHeight = (int) Math.ceil(i.getTop());
            int bHeight = (int) Math.ceil(i.getBottom());

            int lSrcInset = (int) Math.ceil(imgInsets.getLeft());
            int rSrcInset = (int) Math.ceil(imgInsets.getRight());
            int tSrcInset = (int) Math.ceil(imgInsets.getTop());
            int bSrcInset = (int) Math.ceil(imgInsets.getBottom());

            int lSrcWidth = (int) Math.ceil(srcI.getLeft());
            int rSrcWidth = (int) Math.ceil(srcI.getRight());
            int tSrcHeight = (int) Math.ceil(srcI.getTop());
            int bSrcHeight = (int) Math.ceil(srcI.getBottom());

            int srcInnerH = (int) (srcImgProperty.get().getHeight() - tSrcHeight - bSrcHeight - tSrcInset - bSrcInset);
            int srcInnerW = (int) (srcImgProperty.get().getWidth() -  lSrcWidth - rSrcWidth - lSrcInset - rSrcInset);

            pColumn1.setMinWidth( lWidth*scale);
            pColumn1.setMaxWidth( lWidth*scale);
            pColumn1.setPrefWidth(lWidth*scale);
            pColumn3.setMinWidth( rWidth*scale);
            pColumn3.setMaxWidth( rWidth*scale);
            pColumn3.setPrefWidth(rWidth*scale);
            pRow1.setMinHeight(  tHeight*scale);
            pRow1.setMaxHeight(  tHeight*scale);
            pRow1.setPrefHeight( tHeight*scale);
            pRow3.setMinHeight(  bHeight*scale);
            pRow3.setMaxHeight(  bHeight*scale);
            pRow3.setPrefHeight( bHeight*scale);

            boolean stretchH = backgroundHRepeatProperty.get() == BackgroundRepeat.NO_REPEAT;
            boolean stretchV = backgroundVRepeatProperty.get() == BackgroundRepeat.NO_REPEAT;

            BackgroundSize hRepeatBG = new BackgroundSize(BackgroundSize.AUTO, 1, false, true, false, false);
            BackgroundSize vRepeatBG = new BackgroundSize(1, BackgroundSize.AUTO, true, false, false, false);
            BackgroundSize fullBG = new BackgroundSize(1, 1, true, true, false, false);

            tl = new WritableImage(reader, lSrcInset, tSrcInset, lSrcWidth, tSrcHeight);
            tlPane.setBackground(new Background(new BackgroundImage(tl, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, fullBG)));

            bl = new WritableImage(reader, lSrcInset, tSrcInset + tSrcHeight + srcInnerH, lSrcWidth, bSrcHeight);
            blPane.setBackground(new Background(new BackgroundImage(bl, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, fullBG)));

            tr = new WritableImage(reader, lSrcInset + lSrcWidth + srcInnerW, tSrcInset, rSrcWidth, tSrcHeight);
            trPane.setBackground(new Background(new BackgroundImage(tr, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, fullBG)));

            br = new WritableImage(reader, lSrcInset + lSrcWidth + srcInnerW, tSrcInset + tSrcHeight + srcInnerH, rSrcWidth, bSrcHeight);
            brPane.setBackground(new Background(new BackgroundImage(br, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, fullBG)));

            lSrc = new WritableImage(reader, lSrcInset, tSrcInset + tSrcHeight, lSrcWidth, srcInnerH);
            lPane.setBackground(new Background(new BackgroundImage(lSrc, BackgroundRepeat.NO_REPEAT, backgroundVRepeatProperty.get(), BackgroundPosition.CENTER, stretchV ? fullBG : vRepeatBG)));

            rSrc = new WritableImage(reader, lSrcInset + lSrcWidth + srcInnerW, tSrcInset + tSrcHeight, rSrcWidth, srcInnerH);
            rPane.setBackground(new Background(new BackgroundImage(rSrc, BackgroundRepeat.NO_REPEAT, backgroundVRepeatProperty.get(), BackgroundPosition.CENTER, stretchV ? fullBG : vRepeatBG)));

            tSrc = new WritableImage(reader, lSrcInset + lSrcWidth, tSrcInset, srcInnerW, tSrcHeight);
            tPane.setBackground(new Background(new BackgroundImage(tSrc, backgroundHRepeatProperty.get(), BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, stretchH ? fullBG : hRepeatBG)));

            bSrc = new WritableImage(reader, lSrcInset + lSrcWidth, tSrcInset + tSrcHeight + srcInnerH, srcInnerW, bSrcHeight);
            bPane.setBackground(new Background(new BackgroundImage(bSrc, backgroundHRepeatProperty.get(), BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, stretchH ? fullBG : hRepeatBG)));

            cSrc = new WritableImage(reader, lSrcInset + lSrcWidth, tSrcInset + tSrcHeight, srcInnerW, srcInnerH);
            cPane.setBackground(new Background(new BackgroundImage(cSrc, backgroundHRepeatProperty.get(), backgroundVRepeatProperty.get(), BackgroundPosition.CENTER, fullBG)));


//          //if something seems wrong here's some code to sample pixels
//            IntBuffer ib = IntBuffer.allocate(20);
//            br.getPixelReader().getPixels(0, 0, 1, 1, WritablePixelFormat.getIntArgbInstance(), ib, 1);
//            int alpha = ((ib.get() >> 24) & 0xff);
//            int red = ((ib.get() >> 16) & 0xff);
//            int green = ((ib.get() >> 8) & 0xff);
//            int blue = ((ib.get()) & 0xff);
//            debugLabel.setText("P:" + br.getProgress() + ", W:" + br.getWidth() + ", " + alpha + ", " + red + ", " + green + ", " + blue);
        }catch (Exception ex){
            //log.error("what happened?", ex);
        }
    }

    private void addAt(GridPane gp, Node n, int columnIndex, int rowIndex){
        gp.getChildren().add(n);
        GridPane.setConstraints(n, columnIndex, rowIndex);
    }

    public void setBackground(Image img, Insets patchMargins){
        this.controlPatchSizesProperty.set(patchMargins);
        setBackground(img);
    }
    public void setBackground(Image img){
        srcImgProperty.set(img);
        cutImage();
    }
}

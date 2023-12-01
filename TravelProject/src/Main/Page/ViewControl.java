package Main.Page;

public interface ViewControl {

    /**
     * GreyOut View
     */
    public abstract void addGreyOutGlassPane();
    public abstract void darkenBackground(boolean isDarken);
    public abstract void createCourseTable(boolean isFavorite);
}
